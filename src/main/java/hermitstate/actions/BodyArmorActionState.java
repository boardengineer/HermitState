package hermitstate.actions;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hermit.actions.BodyArmorAction;
import savestate.actions.CurrentActionState;

public class BodyArmorActionState implements CurrentActionState {
    private int block;

    public BodyArmorActionState(AbstractGameAction action) {
        this.block = ReflectionHacks.getPrivate(action, BodyArmorAction.class, "block");
    }

    @Override
    public AbstractGameAction loadCurrentAction() {
        return new BodyArmorAction(AbstractDungeon.player, AbstractDungeon.player, 1, block, false);
    }

    @SpirePatch(
            clz = BodyArmorAction.class,
            paramtypez = {},
            method = "update"
    )
    public static class HalfDoneActionPatch {
        public static void Postfix(BodyArmorAction _instance) {
            // Force the action to stay in the the manager until cards are selected
            if (AbstractDungeon.isScreenUp) {
                _instance.isDone = false;
            }
        }
    }
}
