package hermitstate.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hermit.powers.ShadowCloakPower;
import savestate.powers.PowerState;

public class ShadowCloakPowerState extends PowerState {
    public ShadowCloakPowerState(AbstractPower power) {
        super(power);
    }

    @Override
    public AbstractPower loadPower(AbstractCreature targetAndSource) {
        return new ShadowCloakPower(targetAndSource, amount);
    }
}
