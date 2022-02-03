package hermitstate.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hermit.powers.HighNoonPower;
import savestate.powers.PowerState;

public class HighNoonPowerState extends PowerState {
    public HighNoonPowerState(AbstractPower power) {
        super(power);
    }

    @Override
    public AbstractPower loadPower(AbstractCreature targetAndSource) {
        return new HighNoonPower(targetAndSource, amount);
    }
}
