package hermitstate.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hermit.powers.CoalescencePower;
import savestate.powers.PowerState;

public class CoalescencePowerState extends PowerState {
    public CoalescencePowerState(AbstractPower power) {
        super(power);
    }

    @Override
    public AbstractPower loadPower(AbstractCreature targetAndSource) {
        return new CoalescencePower(targetAndSource, amount);
    }
}
