package hermitstate.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.GameActionManager;
import hermit.patches.EndOfTurnPatch;
import hermit.patches.EndOfTurnPatch3;

public class SilentPatchingPatch {
    @SpirePatch(clz = EndOfTurnPatch3.class, method = "Clear")
    public static class MakeSilent {
        @SpirePrefixPatch
        public static SpireReturn doQuietly(GameActionManager m) {
            EndOfTurnPatch.deadon_counter = 0;
            return SpireReturn.Return(SpireReturn.Continue());
        }
    }
}
