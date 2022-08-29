package hermitstate.actions;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hermit.actions.MaliceAction;
import savestate.DamageInfoState;
import savestate.actions.ActionState;
import savestate.actions.CurrentActionState;

public class MaliceActionState implements CurrentActionState {
    private final DamageInfoState info;
    private final int []base;
    private final int targetIndex;

    public MaliceActionState(AbstractGameAction action) {
        DamageInfo actionInfo = ReflectionHacks.getPrivate(action, MaliceAction.class, "info");
        info = new DamageInfoState(actionInfo);
        base = ReflectionHacks.getPrivate(action, MaliceAction.class, "base");

        AbstractCreature target = action.target;
        targetIndex = ActionState.indexForCreature(target);
    }

    @Override
    public AbstractGameAction loadCurrentAction() {
        info.owner = AbstractDungeon.player;

        MaliceAction result = new MaliceAction(ActionState.creatureForIndex(targetIndex), info
                .loadDamageInfo(), base);

        // This should make the action only trigger the second half of the update
        ReflectionHacks
                .setPrivate(result, AbstractGameAction.class, "duration", 0);

        return result;
    }

    @SpirePatch(
            clz = MaliceAction.class,
            paramtypez = {},
            method = "update"
    )
    public static class HalfDoneActionPatch {
        public static void Postfix(MaliceAction _instance) {
            // Force the action to stay in the the manager until cards are selected
            if (AbstractDungeon.isScreenUp) {
                _instance.isDone = false;
            }
        }
    }
}
