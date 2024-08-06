import java.util.ArrayList;
import java.util.Collections;

/**
 * The CardDeck class represents a deck of playing cards and operations related to the deck.
 */
public class CardDeck {

    /** The deck of playing cards. */
    private ArrayList<PlayingCard> deckOfCards;

    /**
     * Constructs a CardDeck with a complete deck of 52 standard playing cards.
     * Initializes the deckOfCards ArrayList.
     */
    public CardDeck() {
        this.deckOfCards = new ArrayList<>();
        PlayingCard.Suit suit;
        for (int i = 0; i < 4; i++) {
            if (i == 0) {
                suit = PlayingCard.Suit.CLUBS;
            } else if (i == 1) {
                suit = PlayingCard.Suit.DIAMONDS;
            } else if (i == 2) {
                suit = PlayingCard.Suit.SPADES;
            } else {
                suit = PlayingCard.Suit.HEARTS;
            }
            for (int j = 2; j < 15; j++) {
                deckOfCards.add(new PlayingCard(j, suit));
            }
        }
    }

    /**
     * Retrieves the deck of cards.
     *
     * @return The ArrayList containing the deck of cards.
     */
    public ArrayList<PlayingCard> getDeckOfCards() {
        return deckOfCards;
    }

    /**
     * Shuffles the deck of cards.
     */
    public void shuffleDeck() {
        Collections.shuffle(deckOfCards);
    }

    /**
     * Draws a card from the deck and adds it to the specified hand. If the deck is empty, regenerates the deck.
     *
     * @param hand The hand to add the drawn card to.
     */
    public void drawCard(HandOfCards hand) {
        if (deckOfCards.isEmpty()) {
            regenerateDeck();
        }
        PlayingCard drawnCard = deckOfCards.remove(0);
        hand.addCard(drawnCard);
    }

    /**
     * Regenerates the deck of cards if it is empty.
     */
    public void regenerateDeck() {
        deckOfCards.clear();
        PlayingCard.Suit suit;
        for (int i = 0; i < 4; i++) {
            if (i == 0) {
                suit = PlayingCard.Suit.CLUBS;
            } else if (i == 1) {
                suit = PlayingCard.Suit.DIAMONDS;
            } else if (i == 2) {
                suit = PlayingCard.Suit.SPADES;
            } else {
                suit = PlayingCard.Suit.HEARTS;
            }
            for (int j = 2; j < 15; j++) {
                deckOfCards.add(new PlayingCard(j, suit));
            }
        }
    }
}
