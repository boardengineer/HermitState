package hermitstate;

import basemod.BaseMod;
import basemod.ReflectionHacks;
import basemod.interfaces.EditCharactersSubscriber;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import battleaimod.BattleAiMod;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import hermit.actions.*;
import hermit.cards.Shortfuse;
import hermit.cards.Snapshot;
import hermit.potions.Eclipse;
import hermit.powers.*;
import hermit.relics.BartenderGlass;
import hermit.relics.PetGhost;
import hermitstate.actions.*;
import hermitstate.cards.ShortFuseState;
import hermitstate.cards.SnapshotState;
import hermitstate.powers.*;
import savestate.CardState;
import savestate.StateFactories;
import savestate.actions.CurrentActionState;
import savestate.powers.PowerState;

import java.util.HashMap;
import java.util.Optional;

import static hermit.characters.hermit.Enums.COLOR_YELLOW;

@SpireInitializer
public class HermitState implements PostInitializeSubscriber, EditRelicsSubscriber, EditCharactersSubscriber {
    public static void initialize() {
        BaseMod.subscribe(new HermitState());
    }

    @Override
    public void receivePostInitialize() {
        populateCurrentActionsFactory();
        populateCardFactories();
        populatePowerFactory();

        BattleAiMod.cardRankMaps.add(HermitPlayOrder.CARD_RANKS);

        // Current behavior would make this a chat option, it won't be interesting out of the box
        HashMap<String, AbstractRelic> sharedRelics = ReflectionHacks
                .getPrivateStatic(RelicLibrary.class, "sharedRelics");

        sharedRelics.remove(PetGhost.ID);
    }

    private void populateCurrentActionsFactory() {
        StateFactories.currentActionByClassMap
                .put(AdaptAction.class, new CurrentActionState.CurrentActionFactories(action -> new AdaptActionState(action)));
        StateFactories.currentActionByClassMap
                .put(CovetAction.class, new CurrentActionState.CurrentActionFactories(action -> new CovetActionState(action)));
        StateFactories.currentActionByClassMap
                .put(CheatAction.class, new CurrentActionState.CurrentActionFactories(action -> new CheatActionState(action)));
        StateFactories.currentActionByClassMap
                .put(LoneWolfAction.class, new CurrentActionState.CurrentActionFactories(action -> new LoneWolfActionState(action)));
        StateFactories.currentActionByClassMap
                .put(MagnumAction.class, new CurrentActionState.CurrentActionFactories(action -> new MagnumActionState(action)));
        StateFactories.currentActionByClassMap
                .put(MaliceAction.class, new CurrentActionState.CurrentActionFactories(action -> new MaliceActionState(action)));
        StateFactories.currentActionByClassMap
                .put(ReprieveAction.class, new CurrentActionState.CurrentActionFactories(action -> new ReprieveActionState(action)));
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
        StateFactories.powerByIdMap
                .put(Bounty.POWER_ID, new PowerState.PowerFactories(power -> new BountyState(power)));
        StateFactories.powerByIdMap
                .put(Bruise.POWER_ID, new PowerState.PowerFactories(power -> new BruiseState(power)));
        StateFactories.powerByIdMap
                .put(BrawlPower.POWER_ID, new PowerState.PowerFactories(power -> new BrawlPowerState(power)));
        StateFactories.powerByIdMap
                .put(ComboPower.POWER_ID, new PowerState.PowerFactories(power -> new ComboPowerState(power)));
        StateFactories.powerByIdMap
                .put(Concentration.POWER_ID, new PowerState.PowerFactories(power -> new ConcentrationState(power)));
        StateFactories.powerByIdMap
                .put(DeterminationPower.POWER_ID, new PowerState.PowerFactories(power -> new DeterminationPowerState(power)));
        StateFactories.powerByIdMap
                .put(Drained.POWER_ID, new PowerState.PowerFactories(power -> new DrainedState(power), json -> new DrainedState(json)));
        StateFactories.powerByIdMap
                .put(EternalPower.POWER_ID, new PowerState.PowerFactories(power -> new EternalPowerState(power), json -> new EternalPowerState(json)));
        StateFactories.powerByIdMap
                .put(FatalDesirePower.POWER_ID, new PowerState.PowerFactories(power -> new FatalDesirePowerState(power)));
        StateFactories.powerByIdMap
                .put(HighNoonPower.POWER_ID, new PowerState.PowerFactories(power -> new HighNoonPowerState(power)));
        StateFactories.powerByIdMap
                .put(HorrorPower.POWER_ID, new PowerState.PowerFactories(power -> new HorrorPowerState(power)));
        StateFactories.powerByIdMap
                .put(OverwhelmingPowerPower.POWER_ID, new PowerState.PowerFactories(power -> new OverwhelmingPowerPowerState(power)));
        StateFactories.powerByIdMap
                .put(PetGhostPower.POWER_ID, new PowerState.PowerFactories(power -> new PetGhostPowerState(power), json -> new PetGhostPowerState(json)));
        StateFactories.powerByIdMap
                .put(Rugged.POWER_ID, new PowerState.PowerFactories(power -> new RuggedState(power)));
        StateFactories.powerByIdMap
                .put(ShadowCloakPower.POWER_ID, new PowerState.PowerFactories(power -> new ShadowCloakPowerState(power)));
        StateFactories.powerByIdMap
                .put(TakeAimPower.POWER_ID, new PowerState.PowerFactories(power -> new TakeAimPowerState(power)));

    }

    @Override
    public void receiveEditRelics() {
        BaseMod.removeRelicFromCustomPool(new PetGhost(), COLOR_YELLOW);
        BaseMod.removeRelicFromCustomPool(new BartenderGlass(), COLOR_YELLOW);

    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.removePotion(Eclipse.POTION_ID);
    }
}