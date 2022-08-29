package hermitstate.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import hermit.patches.VigorPatch;
import hermit.powers.BigShotPower;
import ludicrousspeed.simulator.ActionSimulator;

/**
 * Duplicate Hermit Mod's Vigor patch to work around the simulator's action loop.
 */
public class VigorPowerPatch {
    @SpirePatch(clz = ActionSimulator.class, method = "ActionManageUpdate", paramtypez = {boolean.class})
    public static class DisableHealing {
        @SpirePrefixPatch
        public static void Prefix(boolean shouldLog) {
            GameActionManager m = AbstractDungeon.actionManager;
            if (m.actions.isEmpty() && m.preTurnActions.isEmpty() && m.cardQueue
                    .isEmpty() && (VigorPatch.isActive > 0)) {
                VigorPatch.thisRun = true;
            }
        }

        @SpirePostfixPatch
        public static void Postfix(boolean shouldLog) {
            GameActionManager m = AbstractDungeon.actionManager;
            if (VigorPatch.thisRun) {
                if (AbstractDungeon.player.hasPower(BigShotPower.POWER_ID)) {

                    AbstractPlayer p = AbstractDungeon.player;
                    AbstractPower pow = AbstractDungeon.player.getPower(BigShotPower.POWER_ID);
                    pow.flash();
                    AbstractDungeon.actionManager
                            .addToBottom(new ApplyPowerAction(p, p, new VigorPower(p, pow.amount), pow.amount));
                }
                VigorPatch.thisRun = false;
                VigorPatch.isActive = 0;
            }

        }
    }
}
