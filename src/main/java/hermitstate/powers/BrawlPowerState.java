package hermitstate.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hermit.powers.BrawlPower;
import savestate.powers.PowerState;

public class BrawlPowerState extends PowerState {
    public BrawlPowerState(AbstractPower power) {
        super(power);
    }

    @Override
    public AbstractPower loadPower(AbstractCreature targetAndSource) {
        return new BrawlPower(targetAndSource, amount);
    }
}
