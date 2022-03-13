package hermitstate.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import hermit.cards.MementoCard;

public class CardPatches {
    @SpirePatch(clz = MementoCard.class, method = "upgrade")
    public static class NoUpgradePatch {
        @SpirePrefixPatch
        public static SpireReturn doNothing(MementoCard instance) {
            return SpireReturn.Return(null);
        }
    }
}
