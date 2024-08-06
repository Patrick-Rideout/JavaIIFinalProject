/**
 * Represents a dealer in a card game. The dealer has a hand of cards.
 */
public class Dealer {

    private HandOfCards hand;

    /**
     * Constructs a new Dealer with an empty hand.
     */
    public Dealer() {
        this.hand = new HandOfCards();
    }

    /**
     * Gets the dealer's hand of cards.
     *
     * @return the hand of cards.
     */
    public HandOfCards getHand() {
        return hand;
    }

    /**
     * Sets the dealer's hand of cards.
     *
     * @param hand the new hand of cards.
     */
    public void setHand(HandOfCards hand) {
        this.hand = hand;
    }
}
