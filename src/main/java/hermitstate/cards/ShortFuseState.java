package hermitstate.cards;

import basemod.ReflectionHacks;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.megacrit.cardcrawl.cards.AbstractCard;
import hermit.cards.Shortfuse;
import savestate.CardState;

public class ShortFuseState extends CardState {
    private final int cost_revert;

    public ShortFuseState(AbstractCard card) {
        super(card);

        cost_revert = ReflectionHacks.getPrivate(card, Shortfuse.class, "cost_revert");
    }

    public ShortFuseState(String json) {
        super(json);

        JsonObject parsed = new JsonParser().parse(json).getAsJsonObject();

        cost_revert = parsed.get("cost_revert").getAsInt();
    }

    @Override
    public AbstractCard loadCard() {
        AbstractCard result = super.loadCard();

        ReflectionHacks.setPrivate(result, Shortfuse.class, "cost_revert", cost_revert);

        return result;
    }

    @Override
    public String encode() {
        String result = super.encode();

        JsonObject parsed = new JsonParser().parse(result).getAsJsonObject();

        parsed.addProperty("cost_revert", cost_revert);

        return parsed.toString();
    }
}
