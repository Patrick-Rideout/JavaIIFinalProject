import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * This class handles a single client connection for a blackjack game.
 * It extends the Thread class to handle concurrent connections.
 */
public class BlackJackServerThread extends Thread {
    private Socket clientSocket;
    private BlackJackGame game;
    private int gameStart;

    /**
     * Constructs a new BlackJackServerThread with the specified client socket.
     *
     * @param clientSocket the socket connected to the client
     */
    public BlackJackServerThread(Socket clientSocket) {
        super("BlackJackServerThread");
        this.clientSocket = clientSocket;
        this.game = new BlackJackGame();
        this.gameStart = 0;
    }

    /**
     * Runs the thread, handling client input and game logic.
     */
    public void run() {
        try (
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            JSONObject response = new JSONObject();

            response.put("message", "Game started.");
            response.put("balance", game.getPlayer().getMoney());
            response.put("playerHand", game.getPlayer().getHand().getHandOfCards().toString());
            response.put("dealerHand", game.getDealer().getHand().getHandOfCards().get(0).toString() + ", **");
            out.println(response.toJSONString());
            int bet = 0;

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                JSONObject responseMessage = new JSONObject();
                try {
                    JSONObject request = (JSONObject) new JSONParser().parse(inputLine);
                    String action = (String) request.get("action");

                    String[] actionParts = action.split(" ");
                    if (actionParts[0].equalsIgnoreCase("bet")) {
                        int wantedBet;
                        try {
                            wantedBet = Integer.parseInt(actionParts[1]);
                        } catch (NumberFormatException e) {
                            responseMessage.put("message", "Invalid bet amount. Please enter a valid number.");
                            out.println(responseMessage.toJSONString());
                            continue;
                        }

                        if (wantedBet <= 0 || wantedBet > game.getPlayer().getMoney()) {
                            responseMessage.put("message", "Invalid bet amount. Please enter a valid amount.");
                        } else {
                            game.getPlayer().setMoney(game.getPlayer().getMoney() - wantedBet);
                            bet = wantedBet;
                            responseMessage.put("message", "Bet placed. Choose 'hit' or 'stand'.");
                            responseMessage.put("balance", game.getPlayer().getMoney());
                        }
                    } else if ("hit".equalsIgnoreCase(action)) {
                        game.getCurrentDeck().drawCard(game.getPlayer().getHand());
                        responseMessage.put("message", "You drew a card. Your current hand:");
                        responseMessage.put("balance", game.getPlayer().getMoney());
                        responseMessage.put("playerHand", game.getPlayer().getHand().getHandOfCards().toString());
                        responseMessage.put("dealerHand", game.getDealer().getHand().getHandOfCards().get(0).toString() + ", **");

                        if (isBusted(game.getPlayer().getHand().getHandOfCards())) {
                            if (game.getPlayer().getMoney() == 0) {
                                responseMessage.put("message", "You Busted! You are out of money! GAME OVER!");
                                out.println(responseMessage.toJSONString());
                                break;
                            }
                            responseMessage.put("message", "You busted! Type 'new' to start a new game.");
                            out.println(responseMessage.toJSONString());
                            bet = 0;
                            game.resetGame();
                            continue;
                        }
                    } else if ("stand".equalsIgnoreCase(action)) {
                        while (getHandValue(game.getDealer().getHand().getHandOfCards()) < 17) {
                            game.getCurrentDeck().drawCard(game.getDealer().getHand());
                        }
                        if (isBusted(game.getDealer().getHand().getHandOfCards())) {
                            responseMessage.put("message", "Dealer busted! You win!");
                            game.getPlayer().setMoney(game.getPlayer().getMoney() + bet * 2);
                        } else {
                            int playerValue = getHandValue(game.getPlayer().getHand().getHandOfCards());
                            int dealerValue = getHandValue(game.getDealer().getHand().getHandOfCards());
                            if (playerValue > dealerValue) {
                                responseMessage.put("message", "You win!");
                                game.getPlayer().setMoney(game.getPlayer().getMoney() + bet * 2);
                            } else if (playerValue < dealerValue) {
                                responseMessage.put("message", "Dealer wins!");
                            } else {
                                responseMessage.put("message", "It's a tie! Bet returned.");
                                game.getPlayer().setMoney(game.getPlayer().getMoney() + bet);
                            }
                        }
                        bet = 0;
                        responseMessage.put("balance", game.getPlayer().getMoney());
                        responseMessage.put("playerHand", game.getPlayer().getHand().getHandOfCards().toString());
                        responseMessage.put("dealerHand", game.getDealer().getHand().getHandOfCards().toString());

                        if (game.getPlayer().getMoney() == 0) {
                            responseMessage.put("message", "Dealer Wins! You are out of money! GAME OVER");
                            out.println(responseMessage.toJSONString());
                            break;
                        }

                        out.println(responseMessage.toJSONString());
                        game.resetGame();

                        continue;
                    } else if ("new".equalsIgnoreCase(action)) {
                        game.resetGame();
                        bet = 0;
                        responseMessage.put("message", "New game started. Place your bet.");
                        responseMessage.put("balance", game.getPlayer().getMoney());
                        responseMessage.put("playerHand", game.getPlayer().getHand().getHandOfCards().toString());
                        responseMessage.put("dealerHand", game.getDealer().getHand().getHandOfCards().get(0).toString() + ", **");
                    } else {
                        responseMessage.put("message", "Invalid action. Please type 'bet', 'hit', 'stand', or 'new'.");
                        responseMessage.put("balance", game.getPlayer().getMoney());
                        responseMessage.put("playerHand", game.getPlayer().getHand().getHandOfCards().toString());
                        responseMessage.put("dealerHand", game.getDealer().getHand().getHandOfCards().get(0).toString() + ", **");
                    }

                    out.println(responseMessage.toJSONString());

                } catch (ParseException e) {
                    e.printStackTrace();
                    responseMessage.put("error", "Invalid input format.");
                    responseMessage.put("message", "Invalid input format.");
                    responseMessage.put("balance", game.getPlayer().getMoney());
                    responseMessage.put("playerHand", game.getPlayer().getHand().getHandOfCards().toString());
                    responseMessage.put("dealerHand", game.getDealer().getHand().getHandOfCards().get(0).toString() + ", **");
                    out.println(responseMessage.toJSONString());
                }
            }

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the specified hand is busted (hand value exceeds 21).
     *
     * @param hand the hand to check
     * @return true if the hand is busted, false otherwise
     */
    private boolean isBusted(ArrayList<PlayingCard> hand) {
        return getHandValue(hand) > 21;
    }

    /**
     * Calculates the total value of the specified hand.
     *
     * @param hand the hand whose value to calculate
     * @return the total value of the hand
     */
    private int getHandValue(ArrayList<PlayingCard> hand) {
        int total = 0;
        int aces = 0;
        for (PlayingCard card : hand) {
            int value = card.getValue();
            if (value > 10 && value < 14) {
                total += 10;
            } else if (value == PlayingCard.ACE) {
                aces++;
                total += 11;
            } else {
                total += value;
            }
        }
        while (total > 21 && aces > 0) {
            total -= 10;
            aces--;
        }
        return total;
    }
}
