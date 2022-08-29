package hermitstate;

import basemod.BaseMod;
import basemod.interfaces.EditCharactersSubscriber;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import battleaimod.BattleAiMod;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import hermit.actions.*;
import hermit.cards.AbstractHermitCard;
import hermit.cards.Shortfuse;
import hermit.cards.Snapshot;
import hermit.potions.Eclipse;
import hermit.powers.*;
import hermit.relics.BartenderGlass;
import hermit.relics.Horseshoe;
import hermit.relics.PetGhost;
import hermit.relics.RedScarf;
import hermitstate.actions.*;
import hermitstate.cards.AbstractHermitCardState;
import hermitstate.cards.ShortFuseState;
import hermitstate.cards.SnapshotState;
import hermitstate.heuristics.CursesAndStatusesFirstHeuristic;
import hermitstate.powers.*;
import savestate.CardState;
import savestate.StateElement;
import savestate.StateFactories;
import savestate.actions.ActionState;
import savestate.actions.CurrentActionState;
import savestate.powers.PowerState;

import static hermit.characters.hermit.Enums.COLOR_YELLOW;

@SpireInitializer
public class HermitState implements PostInitializeSubscriber, EditRelicsSubscriber, EditCharactersSubscriber {
    public static void initialize() {
        BaseMod.subscribe(new HermitState());
    }

    @Override
    public void receivePostInitialize() {
        populateCurrentActionsFactory();
        populateActionsFactory();
        populateCardFactories();
        populatePowerFactory();

        BattleAiMod.cardPlayHeuristics.add(HermitPlayOrder.COMPARATOR);

        BattleAiMod.actionHeuristics.put(CovetAction.class, new CursesAndStatusesFirstHeuristic());
        BattleAiMod.actionHeuristics.put(MaliceAction.class, new CursesAndStatusesFirstHeuristic());

        BattleAiMod.additionalValueFunctions
                .add(saveState -> HermitStateElement.getElementScore(saveState));

        StateElement.ElementFactories stateFactories = new StateElement.ElementFactories(() -> new HermitStateElement(), json -> new HermitStateElement(json), jsonObject -> new HermitStateElement(jsonObject));
        StateFactories.elementFactories.put(HermitStateElement.ELEMENT_KEY, stateFactories);
    }

    private void populateActionsFactory() {
        StateFactories.actionByClassMap
                .put(AdaptAction.class, new ActionState.ActionFactories(action -> new AdaptActionState(action)));
    }

    private void populateCurrentActionsFactory() {
        StateFactories.currentActionByClassMap
                .put(AdaptAction.class, new CurrentActionState.CurrentActionFactories(action -> new AdaptActionState(action)));
        StateFactories.currentActionByClassMap
                .put(CovetAction.class, new CurrentActionState.CurrentActionFactories(action -> new CovetActionState(action)));
        StateFactories.currentActionByClassMap
                .put(CheatAction.class, new CurrentActionState.CurrentActionFactories(action -> new CheatActionState(action)));
        StateFactories.currentActionByClassMap
                .put(EclipseAction.class, new CurrentActionState.CurrentActionFactories(action -> new EclipseActionState(action)));
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
        CardState.CardFactories snapshotFactories = new CardState.CardFactories(card -> new SnapshotState(card), json -> new SnapshotState(json), jsonObject -> new SnapshotState(jsonObject));
        StateFactories.cardFactoriesByType
                .put(Snapshot.class, snapshotFactories);
        StateFactories.cardFactoriesByCardId
                .put(Snapshot.ID, snapshotFactories);

        CardState.CardFactories shortFuseFactories = new CardState.CardFactories(card -> new ShortFuseState(card), json -> new ShortFuseState(json), jsonObject -> new ShortFuseState(jsonObject));
        StateFactories.cardFactoriesByType.put(Shortfuse.class, shortFuseFactories);
        StateFactories.cardFactoriesByCardId.put(Shortfuse.ID, shortFuseFactories);

        CardState.CardFactories hermitCardFactories = new CardState.CardFactories(card -> new AbstractHermitCardState(card), json -> new AbstractHermitCardState(json), jsonObject -> new AbstractHermitCardState(jsonObject));
        StateFactories.cardFactoriesByType.put(AbstractHermitCard.class, hermitCardFactories);
        StateFactories.cardFactoriesByTypeName
                .put(AbstractHermitCardState.TYPE_KEY, hermitCardFactories);
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
                .put(CoalescencePower.POWER_ID, new PowerState.PowerFactories(power -> new CoalescencePowerState(power)));
        StateFactories.powerByIdMap
                .put(ComboPower.POWER_ID, new PowerState.PowerFactories(power -> new ComboPowerState(power)));
        StateFactories.powerByIdMap
                .put(Concentration.POWER_ID, new PowerState.PowerFactories(power -> new ConcentrationState(power)));
        StateFactories.powerByIdMap
                .put(DeterminationPower.POWER_ID, new PowerState.PowerFactories(power -> new DeterminationPowerState(power)));
        StateFactories.powerByIdMap
                .put(Drained.POWER_ID, new PowerState.PowerFactories(power -> new DrainedState(power), json -> new DrainedState(json), jsonObject -> new DrainedState(jsonObject)));
        StateFactories.powerByIdMap
                .put(EternalPower.POWER_ID, new PowerState.PowerFactories(power -> new EternalPowerState(power), json -> new EternalPowerState(json), jsonObject -> new EternalPowerState(jsonObject)));
        StateFactories.powerByIdMap
                .put(FatalDesirePower.POWER_ID, new PowerState.PowerFactories(power -> new FatalDesirePowerState(power)));
        StateFactories.powerByIdMap
                .put(HighNoonPower.POWER_ID, new PowerState.PowerFactories(power -> new HighNoonPowerState(power)));
        StateFactories.powerByIdMap
                .put(HorrorPower.POWER_ID, new PowerState.PowerFactories(power -> new HorrorPowerState(power)));
        StateFactories.powerByIdMap
                .put(MaintenanceStrikePower.POWER_ID, new PowerState.PowerFactories(power -> new MaintenanceStrikePowerState(power)));
        StateFactories.powerByIdMap
                .put(OverwhelmingPowerPower.POWER_ID, new PowerState.PowerFactories(power -> new OverwhelmingPowerPowerState(power)));
        StateFactories.powerByIdMap
                .put(PetGhostPower.POWER_ID, new PowerState.PowerFactories(power -> new PetGhostPowerState(power), json -> new PetGhostPowerState(json), jsonObject -> new PetGhostPowerState(jsonObject)));
        StateFactories.powerByIdMap
                .put(Rugged.POWER_ID, new PowerState.PowerFactories(power -> new RuggedState(power)));
        StateFactories.powerByIdMap
                .put(ShadowCloakPower.POWER_ID, new PowerState.PowerFactories(power -> new ShadowCloakPowerState(power)));
        StateFactories.powerByIdMap
                .put(TakeAimPower.POWER_ID, new PowerState.PowerFactories(power -> new TakeAimPowerState(power)));

    }

    @Override
    public void receiveEditRelics() {
        // Start of battle UI with a big selector tends to not work very well
        BaseMod.removeRelic(new RedScarf());

        // Bugged to work once per restart
        BaseMod.removeRelicFromCustomPool(new PetGhost(), COLOR_YELLOW);

        // Too many potions
        BaseMod.removeRelicFromCustomPool(new BartenderGlass(), COLOR_YELLOW);

        // Bugged because of some debuffs being applied at 0 and not cleared, probably missing
        // some ported logic from basemod or stdlib
        BaseMod.removeRelic(new Horseshoe());
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.removePotion(Eclipse.POTION_ID);
    }
}