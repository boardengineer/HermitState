package hermitstate.actions;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hermit.actions.ReprieveAction;
import savestate.actions.CurrentActionState;

public class ReprieveActionState implements CurrentActionState {
    private final int curseTreshold;

    public ReprieveActionState(AbstractGameAction action) {
        this.curseTreshold = ReflectionHacks
                .getPrivate(action, ReprieveAction.class, "curseTreshold");
    }

    @Override
    public AbstractGameAction loadCurrentAction() {
        ReprieveAction result = new ReprieveAction(curseTreshold);

        // This should make the action only trigger the second half of the update
        ReflectionHacks
                .setPrivate(result, AbstractGameAction.class, "duration", 0);

        return result;
    }

    @SpirePatch(
            clz = ReprieveAction.class,
            paramtypez = {},
            method = "update"
    )
    public static class HalfDoneActionPatch {
        public static void Postfix(ReprieveAction _instance) {
            // Force the action to stay in the the manager until cards are selected
            if (AbstractDungeon.isScreenUp) {
                _instance.isDone = false;
            }
        }
    }
}
