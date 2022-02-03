package hermitstate.powers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hermit.powers.Drained;
import savestate.powers.PowerState;

public class DrainedState extends PowerState {
    public final int total;

    public DrainedState(AbstractPower power) {
        super(power);
        total = ((Drained) power).total;
    }

    public DrainedState(String jsonString) {
        super(jsonString);

        JsonObject parsed = new JsonParser().parse(jsonString).getAsJsonObject();

        total = parsed.get("total").getAsByte();
    }

    @Override
    public AbstractPower loadPower(AbstractCreature targetAndSource) {
        Drained result =  new Drained(targetAndSource, targetAndSource, amount);

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
