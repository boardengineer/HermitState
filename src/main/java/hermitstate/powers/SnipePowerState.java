package hermitstate.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hermit.powers.SnipePower;
import savestate.powers.PowerState;

public class SnipePowerState extends PowerState {
    public SnipePowerState(AbstractPower power) {
        super(power);
    }

    @Override
    public AbstractPower loadPower(AbstractCreature targetAndSource) {
        return new SnipePower(targetAndSource, amount);
    }
}
