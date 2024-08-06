import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class BlackJackServerThread extends Thread {
    private Socket clientSocket;
    private BlackJackGame game;

    public BlackJackServerThread(Socket clientSocket) {
        super("BlackJackServerThread");
        this.clientSocket = clientSocket;
        this.game = new BlackJackGame(); // Initialize game instance
    }

    public void run() {
        try (
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            JSONObject response = new JSONObject();

            // Send initial game state
            response.put("message", "Game started.");
            response.put("balance", game.getPlayer().getMoney());
            response.put("playerHand", game.getPlayer().getHand().getHandOfCards().toString());
            response.put("dealerHand", game.getDealer().getHand().getHandOfCards().get(0).toString() + ", **"); // Hide dealer's hand initially
            out.println(response.toJSONString());

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                JSONObject responseMessage = new JSONObject();
                try {
                    JSONObject request = (JSONObject) new JSONParser().parse(inputLine);
                    String action = (String) request.get("action");
                    int bet = request.containsKey("bet") ? Integer.parseInt(request.get("bet").toString()) : 0;

                    if ("bet".equalsIgnoreCase(action)) {
                        if (bet <= 0 || bet > game.getPlayer().getMoney()) {
                            responseMessage.put("message", "Invalid bet amount. Please enter a valid amount.");
                        } else {
                            game.getPlayer().setMoney(game.getPlayer().getMoney() - bet);
                            responseMessage.put("message", "Bet placed. Choose 'hit' or 'stay'.");
                            responseMessage.put("balance", game.getPlayer().getMoney());
                        }
                    } else if ("hit".equalsIgnoreCase(action)) {
                        game.getCurrentDeck().drawCard(game.getPlayer().getHand());
                        responseMessage.put("message", "You drew a card. Your current hand:");
                        responseMessage.put("balance", game.getPlayer().getMoney());
                        responseMessage.put("playerHand", game.getPlayer().getHand().getHandOfCards().toString());
                        responseMessage.put("dealerHand", game.getDealer().getHand().getHandOfCards().get(0).toString() + ", **");

                        if (isBusted(game.getPlayer().getHand().getHandOfCards())) {
                            responseMessage.put("message", "You busted! Game over. Type 'new' to start a new game.");
                            out.println(responseMessage.toJSONString());
                            game.resetGame();
                            continue;
                        }
                    } else if ("stay".equalsIgnoreCase(action)) {
                        while (getHandValue(game.getDealer().getHand().getHandOfCards()) < 17) {
                            game.getCurrentDeck().drawCard(game.getDealer().getHand());
                        }
                        if (isBusted(game.getDealer().getHand().getHandOfCards())) {
                            responseMessage.put("message", "Dealer busted! You win!");
                            game.getPlayer().setMoney(game.getPlayer().getMoney() + bet * 2); // Player wins double the bet
                        } else {
                            int playerValue = getHandValue(game.getPlayer().getHand().getHandOfCards());
                            int dealerValue = getHandValue(game.getDealer().getHand().getHandOfCards());
                            if (playerValue > dealerValue) {
                                responseMessage.put("message", "You win!");
                                game.getPlayer().setMoney(game.getPlayer().getMoney() + bet * 2); // Player wins double the bet
                            } else if (playerValue < dealerValue) {
                                responseMessage.put("message", "Dealer wins!");
                            } else {
                                responseMessage.put("message", "It's a tie! Bet returned.");
                                game.getPlayer().setMoney(game.getPlayer().getMoney() + bet); // Return bet on tie
                            }
                        }
                        responseMessage.put("balance", game.getPlayer().getMoney());
                        responseMessage.put("playerHand", game.getPlayer().getHand().getHandOfCards().toString());
                        responseMessage.put("dealerHand", game.getDealer().getHand().getHandOfCards().toString());
                        out.println(responseMessage.toJSONString());
                        game.resetGame();
                        continue;
                    } else if ("new".equalsIgnoreCase(action)) {
                        game.resetGame();
                        responseMessage.put("message", "New game started. Place your bet.");
                        responseMessage.put("balance", game.getPlayer().getMoney());
                        responseMessage.put("playerHand", game.getPlayer().getHand().getHandOfCards().toString());
                        responseMessage.put("dealerHand", game.getDealer().getHand().getHandOfCards().get(0).toString() + ", **");
                    } else {
                        responseMessage.put("message", "Invalid action. Please type 'bet', 'hit', 'stay', or 'new'.");
                        responseMessage.put("balance", game.getPlayer().getMoney());
                        responseMessage.put("playerHand", game.getPlayer().getHand().getHandOfCards().toString());
                        responseMessage.put("dealerHand", game.getDealer().getHand().getHandOfCards().get(0).toString() + ", **");
                    }

                    // Send updated game state to client
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

    private boolean isBusted(ArrayList<PlayingCard> hand) {
        return getHandValue(hand) > 21;
    }

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
