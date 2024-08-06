/**
 * Represents a game of Blackjack. This class manages the player, dealer, card deck, and game score.
 */
public class BlackJackGame {
    private Player player;
    private Dealer dealer;
    private CardDeck currentDeck;
    private float score;

    /**
     * Constructs a new BlackJackGame with a player, a dealer, a shuffled deck, and deals initial cards.
     */
    public BlackJackGame() {
        score = 0;

        currentDeck = new CardDeck();
        currentDeck.shuffleDeck();

        player = new Player();
        currentDeck.drawCard(player.getHand());
        currentDeck.drawCard(player.getHand());

        dealer = new Dealer();
        currentDeck.drawCard(dealer.getHand());
        currentDeck.drawCard(dealer.getHand());
    }

    /**
     * Resets the game by regenerating and shuffling the deck, and clearing the hands of both the player and the dealer.
     */
    public void resetGame() {
        currentDeck.regenerateDeck();
        currentDeck.shuffleDeck();

        player.getHand().clearHand();
        dealer.getHand().clearHand();

        currentDeck.drawCard(player.getHand());
        currentDeck.drawCard(player.getHand());

        currentDeck.drawCard(dealer.getHand());
        currentDeck.drawCard(dealer.getHand());
    }

    /**
     * Gets the player in the game.
     *
     * @return the player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets the player in the game.
     *
     * @param player the new player.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Gets the dealer in the game.
     *
     * @return the dealer.
     */
    public Dealer getDealer() {
        return dealer;
    }

    /**
     * Sets the dealer in the game.
     *
     * @param dealer the new dealer.
     */
    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    /**
     * Gets the current deck of cards.
     *
     * @return the current deck.
     */
    public CardDeck getCurrentDeck() {
        return currentDeck;
    }

    /**
     * Sets the current deck of cards.
     *
     * @param currentDeck the new deck.
     */
    public void setCurrentDeck(CardDeck currentDeck) {
        this.currentDeck = currentDeck;
    }

    /**
     * Gets the score of the game.
     *
     * @return the score.
     */
    public float getScore() {
        return score;
    }

    /**
     * Sets the score of the game.
     *
     * @param score the new score.
     */
    public void setScore(float score) {
        this.score = score;
    }
}
