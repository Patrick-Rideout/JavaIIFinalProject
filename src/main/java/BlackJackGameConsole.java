import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class allows you to run the Black Jack game in console.
 */
public class BlackJackGameConsole {

    public static void main(String[] args) {

        PrintStream printstream = new PrintStream(System.out);
        Scanner input = new Scanner(System.in);

        CardDeck cardDeck = new CardDeck();
        Dealer dealer = new Dealer();
        Player player = new Player();

        System.out.println("Black Jack! \n");

        while (true) {
            System.out.println("\nSelections:");
            System.out.println("""
                0. Break
                1. Start Game
                """);

            System.out.println("\nENTER SELECTION:");
            String selection = input.nextLine();
            if (selection.equals("0")) {
                break;
            } else if (selection.equals("1")) {

                player.setMoney(100);

                int bet = -1;
                int dealerTotal = 0;
                int playerTotal = 0;

                System.out.println("Game Started!\n");

                while (true) {

                    player.getHand().clearHand();
                    dealer.getHand().clearHand();
                    cardDeck.regenerateDeck();

                    if (player.getMoney() <= 0) {
                        System.out.println("You have run out of money. Game over!\n");
                        break;
                    }

                    System.out.println("Enter Bet:");

                    try {
                        bet = input.nextInt();
                        input.nextLine();
                    } catch (InputMismatchException e) {
                        System.out.println("Please enter a valid number.");
                        input.nextLine();
                        continue;
                    }

                    if (bet > player.getMoney() || bet <= 0) {
                        System.out.printf("Bet must be greater than 0 and less than or equal to %d\n", player.getMoney());
                        continue;
                    }

                    player.setMoney(player.getMoney() - bet);

                    cardDeck.shuffleDeck();

                    cardDeck.drawCard(player.getHand());
                    cardDeck.drawCard(dealer.getHand());
                    cardDeck.drawCard(player.getHand());
                    cardDeck.drawCard(dealer.getHand());

                    while (true) {
                        System.out.printf("Dealers Hand: ");
                        System.out.printf(" (%s) (??)", dealer.getHand().getHandOfCards().get(0));

                        System.out.println();


                        System.out.printf("Players Hand:");
                        for (PlayingCard playingCard : player.getHand().getHandOfCards()) {
                            System.out.printf(" (%s)", playingCard);
                        }
                        System.out.println();

                        int initialCase = validateHand(player.getHand());

                        if (initialCase == 21) {
                            System.out.println("\n----BLACK JACK----\n");
                            player.setMoney(player.getMoney() + (bet*2));

                            break;
                        }

                        System.out.println();

                        System.out.println("Hit(H) or Stand(S):");

                        String hitOrStand = input.nextLine().toUpperCase();

                        if (hitOrStand.equals("H")) {
                            System.out.println("\n----HIT----\n");

                            cardDeck.drawCard(player.getHand());
                            int hitCase = validateHand(player.getHand());

                            if (hitCase > 21) {

                                System.out.printf("Players Hand:");
                                for (PlayingCard playingCard : player.getHand().getHandOfCards()) {
                                    System.out.printf(" (%s)", playingCard);
                                }

                                System.out.println();

                                System.out.println("\n----BUST----\n");

                                break;
                            } else if (hitCase == 21) {

                                System.out.println("\n----BLACK JACK----\n");
                                player.setMoney(player.getMoney() + (bet*2));

                                break;
                            }


                        } else if (hitOrStand.equals("S")) {

                            System.out.println("\n----STAND----\n");

                            int playerStandCase = validateHand(player.getHand());

                            int dealerStandCase = validateHand(dealer.getHand());


                            System.out.printf("Players Hand:");
                            for (PlayingCard playingCard : player.getHand().getHandOfCards()) {
                                System.out.printf(" (%s)", playingCard);
                            }

                            System.out.println();

                            System.out.printf("Dealer Hand:");
                            for (PlayingCard playingCard : dealer.getHand().getHandOfCards()) {
                                System.out.printf(" (%s)", playingCard);
                            }

                            System.out.println();

                            if (playerStandCase > dealerStandCase) {

                                if (dealerStandCase >= 16) {
                                    System.out.println("\n----PLAYER WIN----\n");
                                    player.setMoney(player.getMoney() + (bet*2));

                                    break;
                                } else {
                                    while (true) {
                                        if (dealerStandCase >= 16) {
                                            break;
                                        }
                                        System.out.println("\n----DEALER HIT----\n");
                                        cardDeck.drawCard(dealer.getHand());
                                        dealerStandCase = validateHand(dealer.getHand());
                                    }

                                    System.out.printf("Players Hand:");
                                    for (PlayingCard playingCard : player.getHand().getHandOfCards()) {
                                        System.out.printf(" (%s)", playingCard);
                                    }

                                    System.out.println();

                                    System.out.printf("Dealer Hand:");
                                    for (PlayingCard playingCard : dealer.getHand().getHandOfCards()) {
                                        System.out.printf(" (%s)", playingCard);
                                    }

                                    if (dealerStandCase > playerStandCase) {
                                        if (dealerStandCase <= 21) {
                                            System.out.println("\n----DEALER WIN----\n");

                                            break;
                                        } else if (dealerStandCase > 21) {
                                            System.out.println("\n----PLAYER WIN----\n");
                                           player.setMoney(player.getMoney() + (bet*2));

                                            break;
                                        } else {
                                            System.out.println("\n----PUSH----\n");
                                            player.setMoney(player.getMoney() + (bet));
                                        }


                                    } else if (dealerStandCase < playerStandCase) {
                                        System.out.println("\n----PLAYER WIN----\n");
                                        player.setMoney(player.getMoney() + (bet*2));

                                        break;
                                    } else {
                                        System.out.println("\n----PUSH----\n");
                                        player.setMoney(player.getMoney() + (bet));

                                        }
                                    }

                            } else if (playerStandCase < dealerStandCase) {
                                System.out.println("\n----DEALER WIN----\n");

                                break;
                            } else {
                                System.out.println("\n----PUSH----\n");
                                player.setMoney(player.getMoney() + (bet));

                                break;
                            }

                        }

                    }

                }
            }
        }

    }

    public static int validateHand(HandOfCards hand) {

        int total = 0;
        ArrayList<Integer> PlayingCardValues = new ArrayList<>();

        for (PlayingCard playingCard : hand.getHandOfCards()) {
            PlayingCardValues.add(playingCard.getValue());
        }

        Collections.sort(PlayingCardValues);

        for (int playingCardValue : PlayingCardValues) {

            if (playingCardValue < 14) {
                if (playingCardValue >= 10) {
                    total += 10;
                } else {
                    total += playingCardValue;
                }
            } else {
                if (total + 11 > 21) {
                    total += 1;
                } else {
                    total += 11;
                }
            }
        }
        return total;
    }
}
