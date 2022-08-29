package hermitstate.cards;

import basemod.ReflectionHacks;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.megacrit.cardcrawl.cards.AbstractCard;
import hermit.cards.Snapshot;

public class SnapshotState extends AbstractHermitCardState {
    private final int prev_cost;

    public SnapshotState(AbstractCard card) {
        super(card);

        prev_cost = ReflectionHacks.getPrivate(card, Snapshot.class, "prev_cost");
    }

    public SnapshotState(String json) {
        super(json);

        JsonObject parsed = new JsonParser().parse(json).getAsJsonObject();

        prev_cost = parsed.get("prev_cost").getAsInt();
    }

    public SnapshotState(JsonObject cardJson) {
        super(cardJson);

        prev_cost = cardJson.get("prev_cost").getAsInt();
    }

    @Override
    public AbstractCard loadCard() {
        AbstractCard result = super.loadCard();

        ReflectionHacks.setPrivate(result, Snapshot.class, "prev_cost", prev_cost);

        return result;
    }

    @Override
    public String encode() {
        String result = super.encode();

        JsonObject parsed = new JsonParser().parse(result).getAsJsonObject();

        parsed.addProperty("prev_cost", prev_cost);

        return parsed.toString();
    }

    @Override
    public JsonObject jsonEncode() {
        JsonObject result = super.jsonEncode();

        result.addProperty("prev_cost", prev_cost);

        return result;
    }
}
