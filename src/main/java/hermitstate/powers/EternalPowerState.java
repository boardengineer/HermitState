package hermitstate.powers;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hermit.powers.EternalPower;
import savestate.powers.PowerState;

public class EternalPowerState extends PowerState {
    public final int total;

    public EternalPowerState(AbstractPower power) {
        super(power);
        total = ((EternalPower) power).total;
    }

    public EternalPowerState(String jsonString) {
        super(jsonString);

        JsonObject parsed = new JsonParser().parse(jsonString).getAsJsonObject();

        total = parsed.get("total").getAsByte();
    }

    @Override
    public AbstractPower loadPower(AbstractCreature targetAndSource) {
        EternalPower result = new EternalPower(targetAndSource, targetAndSource, amount);

        result.total = total;
        result.amount = amount;

        return result;
    }

    @Override
    public String encode() {
        JsonObject parsed = new JsonParser().parse(super.encode()).getAsJsonObject();

        parsed.addProperty("total", total);

        return parsed.toString();
    }

    // Patch the way eternal power counts usage, updating amount in updateDescription is
    // problematic.
    @SpirePatch(clz = EternalPower.class, method = "onUseCard")
    public static class UpdateOnUseCard {
        @SpirePrefixPatch
        public static SpireReturn onUseCard(EternalPower power, AbstractCard card, UseCardAction action) {
            if (power.amount > 0) {
                power.flash();

                power.amount = power.total - AbstractDungeon.actionManager.cardsPlayedThisTurn
                        .size();

                power.updateDescription();
            }

            return SpireReturn.Return(null);
        }
    }

    @SpirePatch(clz = EternalPower.class, method = "updateDescription")
    public static class NoAmountChangeInUpdateDescriptionPatch {
        @SpirePrefixPatch
        public static SpireReturn updateDescription(EternalPower power) {
            if (power.amount <= 0) {
                power.description = EternalPower.DESCRIPTIONS[3];
            } else if (power.amount == 1) {
                power.description = EternalPower.DESCRIPTIONS[0] + power.amount + EternalPower.DESCRIPTIONS[1];
            } else {
                power.description = EternalPower.DESCRIPTIONS[0] + power.amount + EternalPower.DESCRIPTIONS[2];
            }

            return SpireReturn.Return(null);
        }
    }

    @SpirePatch(clz = EternalPower.class, method = "atStartOfTurnPostDraw")
    public static class UpdateOnStartOfTurnPatch {
        @SpirePrefixPatch
        public static void updateDescription(EternalPower power) {
            power.amount = power.total - AbstractDungeon.actionManager.cardsPlayedThisTurn
                    .size();
        }
    }

    @SpirePatch(clz = EternalPower.class, method = "stackPower")
    public static class UpdateOnStackPowerPatch {
        @SpirePostfixPatch
        public static void updateDescription(EternalPower power, int stackAmount) {
            power.amount = power.total - AbstractDungeon.actionManager.cardsPlayedThisTurn
                    .size();
        }
    }

    @SpirePatch(clz = EternalPower.class, method = SpirePatch.CONSTRUCTOR)
    public static class UpdateOnConstructorPatch {
        @SpirePostfixPatch
        public static void updateDescription(EternalPower power, AbstractCreature owner, AbstractCreature source, int amount) {
            power.amount = power.total - AbstractDungeon.actionManager.cardsPlayedThisTurn
                    .size();
        }
    }
}
