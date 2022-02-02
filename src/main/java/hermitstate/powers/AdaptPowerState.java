package hermitstate.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hermit.powers.AdaptPower;
import savestate.powers.PowerState;

public class AdaptPowerState extends PowerState {
    public AdaptPowerState(AbstractPower power) {
        super(power);
    }

    @Override
    public AbstractPower loadPower(AbstractCreature targetAndSource) {
        return new AdaptPower(targetAndSource, targetAndSource, amount);
    }
}
