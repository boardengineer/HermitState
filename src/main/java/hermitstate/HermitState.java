package hermitstate;

import basemod.BaseMod;
import basemod.interfaces.PostInitializeSubscriber;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import hermit.actions.CovetAction;
import hermit.cards.Snapshot;
import hermitstate.actions.CovetActionState;
import hermitstate.cards.SnapshotState;
import savestate.CardState;
import savestate.StateFactories;
import savestate.actions.CurrentActionState;

import java.util.Optional;

@SpireInitializer
public class HermitState implements PostInitializeSubscriber {
    public static void initialize() {
        BaseMod.subscribe(new HermitState());
    }

    @Override
    public void receivePostInitialize() {
        populateCurrentActionsFactory();
        populateCardFactories();
    }

    private void populateCurrentActionsFactory() {
        StateFactories.currentActionByClassMap
                .put(CovetAction.class, new CurrentActionState.CurrentActionFactories(action -> new CovetActionState(action)));
    }

    private void populateCardFactories() {
        CardState.CardFactories snapshotFactories = new CardState.CardFactories(card -> {
            if (card instanceof Snapshot) {
                return Optional.of(new SnapshotState(card));
            }
            return Optional.empty();
        }, json -> {
            JsonObject parsed = new JsonParser().parse(json).getAsJsonObject();
            if (parsed.get("card_id").getAsString().equals(Snapshot.ID)) {
                return Optional.of(new SnapshotState(json));
            }
            return Optional.empty();
        });

        StateFactories.cardFactories.add(snapshotFactories);
    }
}