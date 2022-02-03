package hermitstate.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hermit.powers.FatalDesirePower;
import savestate.powers.PowerState;

public class FatalDesirePowerState extends PowerState {
    public FatalDesirePowerState(AbstractPower power) {
        super(power);
    }

    @Override
    public AbstractPower loadPower(AbstractCreature targetAndSource) {
        return new FatalDesirePower(targetAndSource, targetAndSource, amount);
    }
}
