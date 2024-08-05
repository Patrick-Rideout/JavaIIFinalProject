import java.util.ArrayList;
import java.util.Collections;

/**
 * The Hand class represents a collection of playing cards held by a player.
 */
public class HandOfCards {

    /** The hand of playing cards. */
    private ArrayList<PlayingCard> hand;

    /**
     * Constructs an empty hand of cards.
     */
    public HandOfCards() {
        this.hand = new ArrayList<>();
    }

    /**
     * Retrieves the hand of cards.
     *
     * @return The ArrayList containing the hand of cards.
     */
    public ArrayList<PlayingCard> getHandOfCards() {
        return hand;
    }

    /**
     * Adds a card to the hand.
     *
     * @param card The card to add to the hand.
     */
    public void addCard(PlayingCard card) {
        hand.add(card);
    }

    /**
     * Removes a card from the hand.
     *
     * @param card The card to remove from the hand.
     * @return True if the card was removed, false otherwise.
     */
    public boolean removeCard(PlayingCard card) {
        return hand.remove(card);
    }

    /**
     * Clears the hand of cards.
     */
    public void clearHand() {
        hand.clear();
    }

    /**
     * Retrieves the number of cards in the hand.
     *
     * @return The number of cards in the hand.
     */
    public int getHandSize() {
        return hand.size();
    }
}
