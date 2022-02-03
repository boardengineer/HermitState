package hermitstate.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hermit.powers.HorrorPower;
import savestate.powers.PowerState;

public class HorrorPowerState extends PowerState {
    public HorrorPowerState(AbstractPower power) {
        super(power);
    }

    @Override
    public AbstractPower loadPower(AbstractCreature targetAndSource) {
        return new HorrorPower(targetAndSource, amount);
    }
}
