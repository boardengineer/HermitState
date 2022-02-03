package hermitstate.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hermit.powers.Bruise;
import savestate.powers.PowerState;

public class BruiseState extends PowerState {
    public BruiseState(AbstractPower power) {
        super(power);
    }

    @Override
    public AbstractPower loadPower(AbstractCreature targetAndSource) {
        return new Bruise(targetAndSource, amount);
    }
}
