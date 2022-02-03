package hermitstate.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hermit.powers.Bounty;
import savestate.powers.PowerState;

public class BountyState extends PowerState {
    public BountyState(AbstractPower power) {
        super(power);
    }

    @Override
    public AbstractPower loadPower(AbstractCreature targetAndSource) {
        return new Bounty(targetAndSource, amount);
    }
}
