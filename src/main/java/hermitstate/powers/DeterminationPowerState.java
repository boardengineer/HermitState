package hermitstate.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hermit.powers.DeterminationPower;
import savestate.powers.PowerState;

public class DeterminationPowerState extends PowerState {
    public DeterminationPowerState(AbstractPower power) {
        super(power);
    }

    @Override
    public AbstractPower loadPower(AbstractCreature targetAndSource) {
        return new DeterminationPower(targetAndSource, amount);
    }
}
