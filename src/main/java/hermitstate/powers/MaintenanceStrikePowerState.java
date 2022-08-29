package hermitstate.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hermit.powers.MaintenanceStrikePower;
import savestate.powers.PowerState;

public class MaintenanceStrikePowerState extends PowerState {
    public MaintenanceStrikePowerState(AbstractPower power) {
        super(power);
    }

    @Override
    public AbstractPower loadPower(AbstractCreature targetAndSource) {
        return new MaintenanceStrikePower(targetAndSource, amount);
    }
}
