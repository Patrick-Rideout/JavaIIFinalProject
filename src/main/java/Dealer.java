public class Dealer {

    private HandOfCards hand;

    public Dealer() {
        this.hand = new HandOfCards();
    }

    public HandOfCards getHand() {
        return hand;
    }

    public void setHand(HandOfCards hand) {
        this.hand = hand;
    }
}
