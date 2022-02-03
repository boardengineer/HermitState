package hermitstate.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hermit.powers.Rugged;
import savestate.powers.PowerState;

public class RuggedState extends PowerState {
    public RuggedState(AbstractPower power) {
        super(power);
    }

    @Override
    public AbstractPower loadPower(AbstractCreature targetAndSource) {
        return new Rugged(targetAndSource, amount);
    }
}
