package hermitstate.cards;

import basemod.ReflectionHacks;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.megacrit.cardcrawl.cards.AbstractCard;
import hermit.cards.Snapshot;
import savestate.CardState;

public class SnapshotState extends CardState {
    private final boolean wasDeadOn;

    public SnapshotState(AbstractCard card) {
        super(card);

        wasDeadOn = ReflectionHacks.getPrivate(card, Snapshot.class, "wasDeadOn");
    }

    public SnapshotState(String json) {
        super(json);

        JsonObject parsed = new JsonParser().parse(json).getAsJsonObject();

        wasDeadOn = parsed.get("wasDeadOn").getAsBoolean();
    }

    @Override
    public AbstractCard loadCard() {
        AbstractCard result = super.loadCard();

        ReflectionHacks.setPrivate(result, Snapshot.class, "wasDeadOn", wasDeadOn);

        return result;
    }

    @Override
    public String encode() {
        String result = super.encode();

        JsonObject parsed = new JsonParser().parse(result).getAsJsonObject();

        parsed.addProperty("wasDeadOn", wasDeadOn);

        return parsed.toString();
    }
}
