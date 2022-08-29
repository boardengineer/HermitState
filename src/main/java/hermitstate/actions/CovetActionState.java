package hermitstate.actions;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hermit.actions.CovetAction;
import savestate.actions.CurrentActionState;

public class CovetActionState implements CurrentActionState {
    private final int extra_draw;

    public CovetActionState(AbstractGameAction action) {
        this.extra_draw = ReflectionHacks.getPrivate(action, CovetAction.class, "extra_draw");
    }

    @Override
    public AbstractGameAction loadCurrentAction() {
        CovetAction result = new CovetAction(extra_draw);

        // This should make the action only trigger the second half of the update
        ReflectionHacks
                .setPrivate(result, AbstractGameAction.class, "duration", 0);

        return result;
    }

    @SpirePatch(
            clz = CovetAction.class,
            paramtypez = {},
            method = "update"
    )
    public static class HalfDoneActionPatch {
        public static void Postfix(CovetAction _instance) {
            // Force the action to stay in the the manager until cards are selected
            if (AbstractDungeon.isScreenUp) {
                _instance.isDone = false;
            }
        }
    }
}
