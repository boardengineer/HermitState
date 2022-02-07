package hermitstate;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import hermit.patches.EndOfTurnPatch;
import hermit.powers.Bounty;
import savestate.CardState;
import savestate.SaveState;
import savestate.StateElement;

import java.util.HashMap;

public class HermitStateElement implements StateElement {
    // This is empty for now and just sdded here to match the boilerplate with vlaue functions

    public static String ELEMENT_KEY = "HERMIT_MOD_STATE";

    private final int deadon_count;

    private static final String CURSED_WEAPON_ID = "hermit:CursedWeapon";
    private static final String GOLDEN_BULLET_ID = "hermit:GoldenBullet";
    private static final String DEAD_OR_ALIVE_ID = "hermit:DeadOrAlive";

    public HermitStateElement() {
        deadon_count = EndOfTurnPatch.deadon_counter;
    }

    public HermitStateElement(String jsonState) {
        JsonObject parsed = new JsonParser().parse(jsonState).getAsJsonObject();

        this.deadon_count = parsed.get("deadon_count").getAsInt();
    }

    @Override
    public String encode() {
        JsonObject statJson = new JsonObject();

        statJson.addProperty("deadon_count", deadon_count);

        return statJson.toString();
    }

    @Override
    public void restore() {
        EndOfTurnPatch.deadon_counter = deadon_count;
    }

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
            switch (card.cardId) {
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
            switch (card.cardId) {
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
            switch (card.cardId) {
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
            switch (card.cardId) {
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
}
