package hermitstate.cards;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.megacrit.cardcrawl.cards.AbstractCard;
import hermit.cards.AbstractHermitCard;
import savestate.CardState;

public class AbstractHermitCardState extends CardState {
    private final boolean trig_deadon;
    private final int defaultSecondMagicNumber;
    private final int defaultBaseSecondMagicNumber;
    private final boolean upgradedDefaultSecondMagicNumber;
    private final boolean isDefaultSecondMagicNumberModified;

    public static final String TYPE_KEY = "AbstractHermitCard";

    public AbstractHermitCardState(AbstractCard card) {
        super(card);

        AbstractHermitCard realCard = (AbstractHermitCard) card;

        trig_deadon = realCard.trig_deadon;
        defaultSecondMagicNumber = realCard.defaultSecondMagicNumber;
        defaultBaseSecondMagicNumber = realCard.defaultBaseSecondMagicNumber;
        upgradedDefaultSecondMagicNumber = realCard.upgradedDefaultSecondMagicNumber;
        isDefaultSecondMagicNumberModified = realCard.isDefaultSecondMagicNumberModified;
    }

    public AbstractHermitCardState(String json) {
        super(json);

        JsonObject parsed = new JsonParser().parse(json).getAsJsonObject();

        this.trig_deadon = parsed.get("trig_deadon").getAsBoolean();
        this.defaultSecondMagicNumber = parsed.get("defaultSecondMagicNumber").getAsInt();
        this.defaultBaseSecondMagicNumber = parsed.get("defaultBaseSecondMagicNumber").getAsInt();
        this.upgradedDefaultSecondMagicNumber = parsed.get("upgradedDefaultSecondMagicNumber")
                                                      .getAsBoolean();
        this.isDefaultSecondMagicNumberModified = parsed.get("isDefaultSecondMagicNumberModified")
                                                        .getAsBoolean();
    }

    public AbstractHermitCardState(JsonObject cardJson) {
        super(cardJson);

        this.trig_deadon = cardJson.get("trig_deadon").getAsBoolean();
        this.defaultSecondMagicNumber = cardJson.get("defaultSecondMagicNumber").getAsInt();
        this.defaultBaseSecondMagicNumber = cardJson.get("defaultBaseSecondMagicNumber").getAsInt();
        this.upgradedDefaultSecondMagicNumber = cardJson.get("upgradedDefaultSecondMagicNumber")
                                                      .getAsBoolean();
        this.isDefaultSecondMagicNumberModified = cardJson.get("isDefaultSecondMagicNumberModified")
                                                        .getAsBoolean();
    }

    @Override
    public AbstractCard loadCard() {
        AbstractHermitCard result = (AbstractHermitCard) super.loadCard();

        result.trig_deadon = trig_deadon;
        result.defaultSecondMagicNumber = defaultSecondMagicNumber;
        result.defaultBaseSecondMagicNumber = defaultBaseSecondMagicNumber;
        result.upgradedDefaultSecondMagicNumber = upgradedDefaultSecondMagicNumber;
        result.isDefaultSecondMagicNumberModified = isDefaultSecondMagicNumberModified;

        return result;
    }

    @Override
    public String encode() {
        String result = super.encode();

        JsonObject parsed = new JsonParser().parse(result).getAsJsonObject();

        parsed.addProperty("trig_deadon", trig_deadon);
        parsed.addProperty("defaultSecondMagicNumber", defaultSecondMagicNumber);
        parsed.addProperty("defaultBaseSecondMagicNumber", defaultBaseSecondMagicNumber);
        parsed.addProperty("upgradedDefaultSecondMagicNumber", upgradedDefaultSecondMagicNumber);
        parsed.addProperty("isDefaultSecondMagicNumberModified", isDefaultSecondMagicNumberModified);

        parsed.addProperty("type", TYPE_KEY);

        return parsed.toString();
    }

    @Override
    public JsonObject jsonEncode() {
        JsonObject result = super.jsonEncode();

        result.addProperty("trig_deadon", trig_deadon);
        result.addProperty("defaultSecondMagicNumber", defaultSecondMagicNumber);
        result.addProperty("defaultBaseSecondMagicNumber", defaultBaseSecondMagicNumber);
        result.addProperty("upgradedDefaultSecondMagicNumber", upgradedDefaultSecondMagicNumber);
        result.addProperty("isDefaultSecondMagicNumberModified", isDefaultSecondMagicNumberModified);

        result.addProperty("type", TYPE_KEY);

        return result;
    }
}
