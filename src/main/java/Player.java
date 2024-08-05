public class Player {

    private HandOfCards hand;

    private int money;

    public Player() {
        this.hand = new HandOfCards();
        this.money = 100;

    }

    public HandOfCards getHand() {
        return hand;
    }

    public void setHand(HandOfCards hand) {
        this.hand = hand;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
