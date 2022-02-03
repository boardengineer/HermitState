package hermitstate.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hermit.powers.TakeAimPower;
import savestate.powers.PowerState;

public class TakeAimPowerState extends PowerState {
    public TakeAimPowerState(AbstractPower power) {
        super(power);
    }

    @Override
    public AbstractPower loadPower(AbstractCreature targetAndSource) {
        return new TakeAimPower(targetAndSource, amount);
    }
}
