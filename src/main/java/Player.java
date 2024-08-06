/**
 * Represents a player in a card game. Each player has a hand of cards and an amount of money.
 */
public class Player {

    private HandOfCards hand;
    private int money;

    /**
     * Constructs a new Player with an empty hand and a default amount of money.
     */
    public Player() {
        this.hand = new HandOfCards();
        this.money = 100;
    }

    /**
     * Gets the player's hand of cards.
     *
     * @return the hand of cards.
     */
    public HandOfCards getHand() {
        return hand;
    }

    /**
     * Sets the player's hand of cards.
     *
     * @param hand the new hand of cards.
     */
    public void setHand(HandOfCards hand) {
        this.hand = hand;
    }

    /**
     * Gets the player's amount of money.
     *
     * @return the amount of money.
     */
    public int getMoney() {
        return money;
    }

    /**
     * Sets the player's amount of money.
     *
     * @param money the new amount of money.
     */
    public void setMoney(int money) {
        this.money = money;
    }
}
