package hermitstate.powers;

import basemod.ReflectionHacks;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hermit.powers.PetGhostPower;
import savestate.powers.PowerState;

public class PetGhostPowerState extends PowerState {
    private final boolean prepDeath;

    public PetGhostPowerState(AbstractPower power) {
        super(power);

        prepDeath = ReflectionHacks.getPrivate(power, PetGhostPower.class, "prepDeath");
    }

    public PetGhostPowerState(String jsonString) {
        super(jsonString);

        JsonObject parsed = new JsonParser().parse(jsonString).getAsJsonObject();

        prepDeath = parsed.get("prepDeath").getAsBoolean();
    }

    public PetGhostPowerState(JsonObject powerJson) {
        super(powerJson);

        prepDeath = powerJson.get("prepDeath").getAsBoolean();
    }

    @Override
    public AbstractPower loadPower(AbstractCreature targetAndSource) {
        PetGhostPower result = new PetGhostPower(targetAndSource, amount);

        ReflectionHacks.setPrivate(result, PetGhostPower.class, "prepDeath", prepDeath);

        return result;
    }

    @Override
    public String encode() {
        JsonObject parsed = new JsonParser().parse(super.encode()).getAsJsonObject();

        parsed.addProperty("prepDeath", prepDeath);

        return parsed.toString();
    }

    @Override
    public JsonObject jsonEncode() {
        JsonObject result = super.jsonEncode();

        result.addProperty("prepDeath", prepDeath);

        return result;
    }
}
