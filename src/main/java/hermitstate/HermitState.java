package hermitstate;

import basemod.BaseMod;
import basemod.interfaces.PostInitializeSubscriber;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import hermit.actions.AdaptAction;
import hermit.actions.CovetAction;
import hermit.actions.LoneWolfAction;
import hermit.cards.Shortfuse;
import hermit.cards.Snapshot;
import hermit.powers.*;
import hermitstate.actions.AdaptActionState;
import hermitstate.actions.CovetActionState;
import hermitstate.actions.LoneWolfActionState;
import hermitstate.cards.ShortFuseState;
import hermitstate.cards.SnapshotState;
import hermitstate.powers.*;
import savestate.CardState;
import savestate.StateFactories;
import savestate.actions.CurrentActionState;
import savestate.powers.PowerState;

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
        populatePowerFactory();
    }

    private void populateCurrentActionsFactory() {
        StateFactories.currentActionByClassMap
                .put(AdaptAction.class, new CurrentActionState.CurrentActionFactories(action -> new AdaptActionState(action)));
        StateFactories.currentActionByClassMap
                .put(CovetAction.class, new CurrentActionState.CurrentActionFactories(action -> new CovetActionState(action)));

        // Eclipse Action grid select: potion eclipse

        StateFactories.currentActionByClassMap
                .put(LoneWolfAction.class, new CurrentActionState.CurrentActionFactories(action -> new LoneWolfActionState(action)));

        // MagnumAction hand select: Card : Magnum

        // MaliceAction hand select: Card : Malice

        // RedScarfAction grid select : relic: Red Scarf

        // ReprieveAction grid select: Card : Reprieve

    }

    private void populateCardFactories() {
        // TODO add a single lookup instead of a bunch of conditionals
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

        CardState.CardFactories shortFuseFactories = new CardState.CardFactories(card -> {
            if (card instanceof Shortfuse) {
                return Optional.of(new ShortFuseState(card));
            }
            return Optional.empty();
        }, json -> {
            JsonObject parsed = new JsonParser().parse(json).getAsJsonObject();
            if (parsed.get("card_id").getAsString().equals(Shortfuse.ID)) {
                return Optional.of(new ShortFuseState(json));
            }
            return Optional.empty();
        });

        StateFactories.cardFactories.add(snapshotFactories);
        StateFactories.cardFactories.add(shortFuseFactories);
    }

    private void populatePowerFactory() {
        StateFactories.powerByIdMap
                .put(AdaptPower.POWER_ID, new PowerState.PowerFactories(power -> new AdaptPowerState(power)));
        StateFactories.powerByIdMap
                .put(BigShotPower.POWER_ID, new PowerState.PowerFactories(power -> new BigShotPowerState(power)));

        // Bounty - card: dead or alive
        // BrawlPower - card: brawl

        StateFactories.powerByIdMap
                .put(Bruise.POWER_ID, new PowerState.PowerFactories(power -> new BruiseState(power)));

        // ComboPower - card: Combo

        StateFactories.powerByIdMap
                .put(Concentration.POWER_ID, new PowerState.PowerFactories(power -> new ConcentrationState(power)));
        StateFactories.powerByIdMap
                .put(DeterminationPower.POWER_ID, new PowerState.PowerFactories(power -> new DeterminationPowerState(power)));

        // Drained - card: NoHoldsBarred
        // EternalPower - card: EternalForm
        // FatalDesirePower - card: FatalDesire
        // HighNoonPower - card: HighNoon

        StateFactories.powerByIdMap
                .put(HorrorPower.POWER_ID, new PowerState.PowerFactories(power -> new HorrorPowerState(power)));


        StateFactories.powerByIdMap
                .put(OverwhelmingPowerPower.POWER_ID, new PowerState.PowerFactories(power -> new OverwhelmingPowerPowerState(power)));


        // PetGhostPower - relic: pet ghost

        StateFactories.powerByIdMap
                .put(Rugged.POWER_ID, new PowerState.PowerFactories(power -> new RuggedState(power)));

        // RyeStalkPower - ?
        // ShadowCloakPower - card: Shadow Cloak

        StateFactories.powerByIdMap
                .put(TakeAimPower.POWER_ID, new PowerState.PowerFactories(power -> new TakeAimPowerState(power)));

    }
}