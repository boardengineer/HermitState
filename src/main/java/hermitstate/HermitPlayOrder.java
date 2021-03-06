package hermitstate;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Pain;
import hermit.cards.*;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

public class HermitPlayOrder {
    public static final HashMap<String, Integer> CARD_RANKS = new HashMap<String, Integer>() {{
        int size = 0;

        put(Pain.ID, size++);

        // powers
        put(EternalForm.ID, size++);
        put(OverwhelmingPower.ID, size++);
        put(Adapt.ID, size++);
        put(TakeAim.ID, size++);
        put(Brawl.ID, size++);
        put(ShadowCloak.ID, size++);
        put(FatalDesire.ID, size++);
        put(Determination.ID, size++);
        put(BigShot.ID, size++);
        put(Scavenge.ID, size++);
        put(Maintenance.ID, size++);
        put(HighNoon.ID, size++);
        put(Combo.ID, size++);
        put(HeroicBravado.ID, size++);
        put(Reprieve.ID, size++);

        put(MementoCard.ID, size++);

        // Big booms
        put(Showdown.ID, size++);
        put(RoundhouseKick.ID, size++);
        put(Shortfuse.ID, size++);
        put(Magnum.ID, size++);
        put(NoHoldsBarred.ID, size++);
        put(Roughhouse.ID, size++);
        put(Malice.ID, size++);
        put(ItchyTrigger.ID, size++);
        put(Roulette.ID, size++);

        // Amplify Effects and X cards
        put(Coalescence.ID, size++);
        put(DeadOrAlive.ID, size++);
        put(EyeOfTheStorm.ID, size++);
        put(Feint.ID, size++);
        put(Glare.ID, size++);
        put(TakeCover.ID, size++);

        // Draw Effects and other acceleration effects
        put(FullyLoaded.ID, size++);
        put(Gambit.ID, size++);
        put(LuckOfTheDraw.ID, size++);
        put(LoneWolf.ID, size++);
        put(Snipe.ID, size++);
        put(DeadMansHand.ID, size++);
        put(Covet.ID, size++);

        // Attacks
        put(CalledShot.ID, size++);
        put(Cheat.ID, size++);
        put(BlackWind.ID, size++);
        put(FromBeyond.ID, size++);
        put(Grudge.ID, size++);
        put(GoldenBullet.ID, size++);
        put(CursedWeapon.ID, size++);
        put(TrackingShot.ID, size++);
        put(PistolWhip.ID, size++);
        put(Desperado.ID, size++);
        put(Enervate.ID, size++);
        put(Misfire.ID, size++);
        put(WideOpen.ID, size++);
        put(HighCaliber.ID, size++);
        put(Quickdraw.ID, size++);

        put(Spite.ID, size++);
        put(SprayPray.ID, size++);
        put(Snapshot.ID, size++);
        put(Ricochet.ID, size++);
        put(FinalCanter.ID, size++);
        put(Deadeye.ID, size++);
        put(Strike_Hermit.ID, size++);

        // Blocks
        put(FlashPowder.ID, size++);
        put(BodyArmor.ID, size++);
        put(Dive.ID, size++);
        put(Gestalt.ID, size++);
        put(GhostlyPresence.ID, size++);
        put(Manifest.ID, size++);
        put(Midnight.ID, size++);
        put(Vantage.ID, size++);
        put(Virtue.ID, size++);
        put(Dissolve.ID, size++);
        put(Defend_Hermit.ID, size++);

        // Unplayables
        put(ImpendingDoom.ID, size++);
    }};

    private static boolean isDeadOnAndRelevant(AbstractCard card) {
        if (card instanceof AbstractHermitCard) {
            if (((AbstractHermitCard) card).isDeadOnPos()) {
                return DEAD_ON_CARDS.contains(card.cardID);
            }
        }

        return false;
    }

    public static HashSet<String> DEAD_ON_CARDS = new HashSet<String>() {
        {
            this.add(Snapshot.ID);
            this.add(CalledShot.ID);
            this.add(Dive.ID);
            this.add(Headshot.ID);
            this.add(ItchyTrigger.ID);
            this.add(Vantage.ID);
            this.add(Cheat.ID);
            this.add(Deadeye.ID);
            this.add(Enervate.ID);
            this.add(EyeOfTheStorm.ID);
            this.add(GhostlyPresence.ID);
            this.add(GoldenBullet.ID);
            this.add(Scavenge.ID);
            this.add(Vantage.ID);
            this.add(Roughhouse.ID);
        }
    };
    public static final Comparator<AbstractCard> COMPARATOR = (card1, card2) -> {
        boolean deadEye1 = isDeadOnAndRelevant(card1);
        boolean deadEye2 = isDeadOnAndRelevant(card2);

        if (deadEye1) {
            if (!deadEye2) {
                return -1;
            }
        } else if (deadEye2) {
            return 1;
        }


        if (CARD_RANKS.containsKey(card1.cardID) && CARD_RANKS
                .containsKey(card2.cardID)) {
            return CARD_RANKS.get(card1.cardID) - CARD_RANKS
                    .get(card2.cardID);
        }
        return 0;
    };
}
