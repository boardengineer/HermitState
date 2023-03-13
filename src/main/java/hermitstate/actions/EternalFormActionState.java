package hermitstate.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import hermit.actions.EternalFormAction;
import savestate.actions.ActionState;

public class EternalFormActionState implements ActionState {
    private final int amount;

    public EternalFormActionState(AbstractGameAction action) {
        this.amount = action.amount;
    }


    @Override
    public AbstractGameAction loadAction() {
        return new EternalFormAction(amount);
    }
}
