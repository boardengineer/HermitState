package hermitstate.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hermit.powers.OverwhelmingPowerPower;
import savestate.powers.PowerState;

public class OverwhelmingPowerPowerState extends PowerState {
    public OverwhelmingPowerPowerState(AbstractPower power) {
        super(power);
    }

    @Override
    public AbstractPower loadPower(AbstractCreature targetAndSource) {
        return new OverwhelmingPowerPower(targetAndSource, targetAndSource, amount);
    }
}
