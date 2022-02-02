package hermitstate.actions;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hermit.actions.LoneWolfAction;
import savestate.actions.CurrentActionState;

public class LoneWolfActionState implements CurrentActionState {
    public LoneWolfActionState(AbstractGameAction action) {
    }

    @Override
    public AbstractGameAction loadCurrentAction() {
        LoneWolfAction result = new LoneWolfAction();

        // This should make the action only trigger the second half of the update
        ReflectionHacks
                .setPrivate(result, AbstractGameAction.class, "duration", 0);

        return result;
    }

    @SpirePatch(
            clz = LoneWolfAction.class,
            paramtypez = {},
            method = "update"
    )
    public static class HalfDoneActionPatch {
        public static void Postfix(LoneWolfAction _instance) {
            // Force the action to stay in the the manager until cards are selected
            if (AbstractDungeon.isScreenUp) {
                _instance.isDone = false;
            }
        }
    }
}
