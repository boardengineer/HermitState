package hermitstate.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hermit.powers.Concentration;
import savestate.powers.PowerState;

public class ConcentrationState extends PowerState {
    public ConcentrationState(AbstractPower power) {
        super(power);
    }

    @Override
    public AbstractPower loadPower(AbstractCreature targetAndSource) {
        return new Concentration(targetAndSource, amount);
    }
}
