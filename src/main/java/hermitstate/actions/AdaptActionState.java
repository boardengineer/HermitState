package hermitstate.actions;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hermit.actions.AdaptAction;
import savestate.actions.CurrentActionState;

public class AdaptActionState implements CurrentActionState {
    private final boolean isRandom;
    private final boolean anyNumber;
    private final boolean canPickZero;
    int block;

    public AdaptActionState(AbstractGameAction action) {
        this.isRandom = ReflectionHacks.getPrivate(action, AdaptAction.class, "isRandom");
        this.anyNumber = ReflectionHacks.getPrivate(action, AdaptAction.class, "anyNumber");
        this.canPickZero = ReflectionHacks.getPrivate(action, AdaptAction.class, "canPickZero");
        this.block = ReflectionHacks.getPrivate(action, AdaptAction.class, "block");
    }

    @Override
    public AbstractGameAction loadCurrentAction() {
        AdaptAction result = new AdaptAction(block, isRandom, anyNumber, canPickZero);

        // This should make the action only trigger the second half of the update
        ReflectionHacks
                .setPrivate(result, AbstractGameAction.class, "duration", 0);

        return result;
    }

    @SpirePatch(
            clz = AdaptAction.class,
            paramtypez = {},
            method = "update"
    )
    public static class HalfDoneActionPatch {
        public static void Postfix(AdaptAction _instance) {
            // Force the action to stay in the the manager until cards are selected
            if (AbstractDungeon.isScreenUp) {
                _instance.isDone = false;
            }
        }
    }
}
