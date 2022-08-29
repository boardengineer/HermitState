package hermitstate;

import com.google.gson.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hermit.patches.EndOfTurnPatch;
import hermit.patches.SelectScreenPatch;
import hermit.patches.VigorPatch;
import hermit.powers.Bounty;
import savestate.CardState;
import savestate.SaveState;
import savestate.StateElement;
import savestate.StateFactories;

import java.util.ArrayList;
import java.util.HashMap;

public class HermitStateElement implements StateElement {
    // This is empty for now and just sdded here to match the boilerplate with vlaue functions

    public static String ELEMENT_KEY = "HERMIT_MOD_STATE";

    private final int deadon_count;
    private final boolean vigorThisRun;
    private final int vigorIsActive;

    private static final String CURSED_WEAPON_ID = "hermit:CursedWeapon";
    private static final String GOLDEN_BULLET_ID = "hermit:GoldenBullet";
    private static final String DEAD_OR_ALIVE_ID = "hermit:DeadOrAlive";

    public final int[] handCloneIndeces;

    public HermitStateElement() {
        deadon_count = EndOfTurnPatch.deadon_counter;
        this.vigorThisRun = VigorPatch.thisRun;
        this.vigorIsActive = VigorPatch.isActive;

        if (SelectScreenPatch.handClone != null && AbstractDungeon.screen == AbstractDungeon.CurrentScreen.HAND_SELECT) {
//            System.err.println("storing the hand clone");
            this.handCloneIndeces = handIndexArrayForHand(SelectScreenPatch.handClone.group);
        } else {
            this.handCloneIndeces = null;
        }
    }

    public HermitStateElement(String jsonState) {
        JsonObject parsed = new JsonParser().parse(jsonState).getAsJsonObject();

        this.deadon_count = parsed.get("deadon_count").getAsInt();
        this.vigorIsActive = parsed.get("deadon_count").getAsInt();
        this.vigorThisRun = parsed.get("deadon_count").getAsBoolean();

        this.handCloneIndeces = null;
    }

    public HermitStateElement(JsonObject elementJson) {
        this.deadon_count = elementJson.get("deadon_count").getAsInt();
        this.vigorIsActive = elementJson.get("deadon_count").getAsInt();
        this.vigorThisRun = elementJson.get("deadon_count").getAsBoolean();

        JsonElement tempHandClone = elementJson.get("hand_clone");
        if (tempHandClone.isJsonNull()) {
            handCloneIndeces = null;
        } else {
            handCloneIndeces = fromJsonArray(elementJson.get("hand_clone").getAsJsonArray());
        }
    }

    @Override
    public String encode() {
        JsonObject statJson = new JsonObject();

        statJson.addProperty("deadon_count", deadon_count);
        statJson.addProperty("vigor_this_run", vigorThisRun);
        statJson.addProperty("vigor_is_active", vigorIsActive);

        if (handCloneIndeces == null) {
            statJson.add("hand_clone", JsonNull.INSTANCE);
        } else {
            statJson.addProperty("hand_clone", toJsonArray(handCloneIndeces).toString());
        }

        return statJson.toString();
    }

    @Override
    public JsonObject jsonEncode() {
        JsonObject statJson = new JsonObject();

        statJson.addProperty("deadon_count", deadon_count);
        statJson.addProperty("vigor_this_run", vigorThisRun);
        statJson.addProperty("vigor_is_active", vigorIsActive);

        if (handCloneIndeces == null) {
            statJson.add("hand_clone", JsonNull.INSTANCE);
        } else {
            statJson.add("hand_clone", toJsonArray(handCloneIndeces));
        }

        return statJson;
    }

    @Override
    public void restore() {
        EndOfTurnPatch.deadon_counter = deadon_count;
        VigorPatch.isActive = vigorIsActive;
        VigorPatch.thisRun = vigorThisRun;

        if (handCloneIndeces == null) {
            SelectScreenPatch.handClone = null;
        } else {
            SelectScreenPatch.handClone = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            SelectScreenPatch.handClone.group = handForIndexArray(handCloneIndeces);
        }
    }

//    @SpirePatch(clz = SelectScreenPatch.class, method = "ResetHand")
//    public static class doThingClass {
//        @SpirePrefixPatch
//        public static void spy() {
//            System.err.println("resetting hand");
//        }
//    }

    public static int getElementScore(SaveState saveState) {
        // We want to prioritize playing and growing cursed weapon but there needs to be an offset
        // the AI eats some HP closs to grow the weapon.
        int cursedWeaponDamage = 0;

        // A negative value will be applied to golden bullet cost to encourage lethal plays
        int goldenBulletCost = 0;

        // Keep score for dead or alive: the bounty power will be added as part of the power mapping
        // below, a score will be assigned to the amount of reamaning DoA cards
        int deadOrAliveCount = 0;


        for (CardState card : saveState.playerState.hand) {
            switch (StateFactories.cardIds[card.cardIdIndex]) {
                case CURSED_WEAPON_ID:
                    cursedWeaponDamage += card.misc;
                    break;
                case GOLDEN_BULLET_ID:
                    goldenBulletCost += card.cost;
                    break;
                case DEAD_OR_ALIVE_ID:
                    deadOrAliveCount++;
                    break;
                default:
                    break;
            }
        }

        for (CardState card : saveState.playerState.drawPile) {
            switch (StateFactories.cardIds[card.cardIdIndex]) {
                case CURSED_WEAPON_ID:
                    cursedWeaponDamage += card.misc;
                    break;
                case GOLDEN_BULLET_ID:
                    goldenBulletCost += card.cost;
                    break;
                case DEAD_OR_ALIVE_ID:
                    deadOrAliveCount++;
                    break;
                default:
                    break;
            }
        }

        for (CardState card : saveState.playerState.discardPile) {
            switch (StateFactories.cardIds[card.cardIdIndex]) {
                case CURSED_WEAPON_ID:
                    cursedWeaponDamage += card.misc;
                    break;
                case GOLDEN_BULLET_ID:
                    goldenBulletCost += card.cost;
                    break;
                case DEAD_OR_ALIVE_ID:
                    deadOrAliveCount++;
                    break;
                default:
                    break;
            }
        }

        for (CardState card : saveState.playerState.exhaustPile) {
            switch (StateFactories.cardIds[card.cardIdIndex]) {
                case CURSED_WEAPON_ID:
                    cursedWeaponDamage += card.misc;
                    break;
                case GOLDEN_BULLET_ID:
                    goldenBulletCost += card.cost;
                    break;
                default:
                    break;
            }
        }

        int powerScore = saveState.playerState.powers.stream()
                                                     .map(powerState -> POWER_VALUES
                                                             .getOrDefault(powerState.powerId, 0) * powerState.amount)
                                                     .reduce(Integer::sum)
                                                     .orElse(0);

        int cursedWeaponScore = cursedWeaponDamage * 10;
        int goldenBulletScore = goldenBulletCost * -20;
        int deadOrAliveScore = deadOrAliveCount * 10;

        return cursedWeaponScore + goldenBulletScore + powerScore + deadOrAliveScore;
    }

    public static final HashMap<String, Integer> POWER_VALUES = new HashMap<String, Integer>() {{
        // Bounty is effectively gold
        put(Bounty.POWER_ID, 2);
    }};

    private static int[] handIndexArrayForHand(ArrayList<AbstractCard> hand) {
        int size = hand.size();
        int[] result = new int[size];

        for (int i = 0; i < size; i++) {
            result[i] = handIndexForCard(hand.get(i));
        }

        return result;
    }

    private static int handIndexForCard(AbstractCard card) {
        int cardIndex = 0;
        for (int i = 0; i < AbstractDungeon.player.hand.size(); i++) {
            if (AbstractDungeon.player.hand.group.get(i) == card) {
                return cardIndex;
            }
            cardIndex++;
        }

        for (int i = 0; i < AbstractDungeon.handCardSelectScreen.selectedCards.group.size(); i++) {
            if (AbstractDungeon.handCardSelectScreen.selectedCards.group.get(i) == card) {
                return cardIndex;
            }
            cardIndex++;
        }

        return -1;
    }

    private static AbstractCard cardForHandIndex(int index) {
        if (index == -1) {
            return null;
        }
        if (index < AbstractDungeon.player.hand.group.size()) {
            return AbstractDungeon.player.hand.group.get(index);
        }
        int newIndex = index - AbstractDungeon.player.hand.group.size();

        return AbstractDungeon.handCardSelectScreen.selectedCards.group.get(newIndex);
    }

    private static ArrayList<AbstractCard> handForIndexArray(int[] indeces) {
        int size = indeces.length;
        ArrayList<AbstractCard> result = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            AbstractCard card = cardForHandIndex(indeces[i]);
            if (card != null) {
                result.add(card);
            }
        }

        return result;
    }

    private static JsonArray toJsonArray(int[] indeces) {
        JsonArray result = new JsonArray();

        for (int i = 0; i < indeces.length; i++) {
            result.add(indeces[i]);
        }

        return result;
    }

    private static int[] fromJsonArray(JsonArray indecesArray) {
        int size = indecesArray.size();
        int[] result = new int[size];

        for (int i = 0; i < size; i++) {
            result[i] = indecesArray.get(i).getAsInt();
        }

        return result;
    }
}
