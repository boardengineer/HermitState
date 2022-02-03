package hermitstate.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hermit.powers.ComboPower;
import savestate.powers.PowerState;

public class ComboPowerState extends PowerState {
    public ComboPowerState(AbstractPower power) {
        super(power);
    }

    @Override
    public AbstractPower loadPower(AbstractCreature targetAndSource) {
        return new ComboPower(targetAndSource, amount);
    }
}
