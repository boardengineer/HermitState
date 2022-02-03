package hermitstate.powers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.megacrit.cardcrawl.core.AbstractCreature;
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
        EternalPower result =  new EternalPower(targetAndSource, targetAndSource, amount);

        result.total = total;

        return  result;
    }

    @Override
    public String encode() {
        JsonObject parsed = new JsonParser().parse(super.encode()).getAsJsonObject();

        parsed.addProperty("total", total);

        return parsed.toString();
    }
}
