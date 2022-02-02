package hermitstate.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hermit.powers.BigShotPower;
import savestate.powers.PowerState;

public class BigShotPowerState extends PowerState {
    public BigShotPowerState(AbstractPower power) {
        super(power);
    }

    @Override
    public AbstractPower loadPower(AbstractCreature targetAndSource) {
        return new BigShotPower(targetAndSource, amount);
    }
}
