public class BlackJackGame {
    private Player player;
    private Dealer dealer;
    private CardDeck currentDeck;
    private float score;

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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    public CardDeck getCurrentDeck() {
        return currentDeck;
    }

    public void setCurrentDeck(CardDeck currentDeck) {
        this.currentDeck = currentDeck;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}