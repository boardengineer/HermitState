package hermitstate.actions;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hermit.actions.MagnumAction;
import savestate.CardState;
import savestate.DamageInfoState;
import savestate.actions.ActionState;
import savestate.actions.CurrentActionState;

public class MagnumActionState implements CurrentActionState {
    private final int magnumcardIndex;
    private final DamageInfoState info;
    private final int targetIndex;

    public MagnumActionState(AbstractGameAction action) {
        AbstractCard magnum = ReflectionHacks.getPrivate(action, MagnumAction.class, "magnumcard");
        magnumcardIndex = CardState.indexForCard(magnum);

        DamageInfo actionInfo = ReflectionHacks.getPrivate(action, MagnumAction.class, "info");
        info = new DamageInfoState(actionInfo);

        targetIndex = ActionState.indexForCreature(action.target);
    }

    @Override
    public AbstractGameAction loadCurrentAction() {
        MagnumAction result = new MagnumAction(ActionState
                .creatureForIndex(targetIndex), info.loadDamageInfo(), CardState
                .cardForIndex(magnumcardIndex));

        // This should make the action only trigger the second half of the update
        ReflectionHacks
                .setPrivate(result, AbstractGameAction.class, "duration", 0);

        return result;
    }

    @SpirePatch(
            clz = MagnumAction.class,
            paramtypez = {},
            method = "update"
    )
    public static class HalfDoneActionPatch {
        public static void Postfix(MagnumAction _instance) {
            // Force the action to stay in the the manager until cards are selected
            if (AbstractDungeon.isScreenUp) {
                _instance.isDone = false;
            }
        }
    }
}
