package hermitstate.actions;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hermit.actions.CheatAction;
import savestate.CardState;
import savestate.actions.CurrentActionState;

public class CheatActionState implements CurrentActionState {
    private int onlyChoiceIndex;
    private boolean onlyBoolean;
    private final boolean isdeadon;
    private final int amount;

    public CheatActionState(AbstractGameAction action) {
        this.onlyBoolean = ReflectionHacks.getPrivate(action, CheatAction.class, "onlyBoolean");

        if (onlyBoolean) {
            AbstractCard onlyChoice = ReflectionHacks
                    .getPrivate(action, CheatAction.class, "onlyChoice");
            onlyChoiceIndex = CardState.indexForCard(onlyChoice);
        }

        this.isdeadon = ReflectionHacks.getPrivate(action, CheatAction.class, "isdeadon");
        this.amount = action.amount;
    }

    @Override
    public AbstractGameAction loadCurrentAction() {
        // The action stores a reference to the cheat card but doesn't use it
        CheatAction result = new CheatAction(amount, null, isdeadon);

        // This should make the action only trigger the second half of the update
        ReflectionHacks
                .setPrivate(result, AbstractGameAction.class, "duration", 0);

        ReflectionHacks
                .setPrivate(result, CheatAction.class, "onlyBoolean", onlyBoolean);
        if (onlyBoolean) {
            AbstractCard onlyChoice = CardState.cardForIndex(onlyChoiceIndex);
            ReflectionHacks
                    .setPrivate(result, CheatAction.class, "onlyChoice", onlyChoice);
        }
        ReflectionHacks
                .setPrivate(result, CheatAction.class, "isdeadon", isdeadon);

        return result;
    }

    @SpirePatch(
            clz = CheatAction.class,
            paramtypez = {},
            method = "update"
    )
    public static class HalfDoneActionPatch {
        public static void Postfix(CheatAction _instance) {
            // Force the action to stay in the the manager until cards are selected
            if (AbstractDungeon.isScreenUp) {
                _instance.isDone = false;
            }
        }
    }
}
