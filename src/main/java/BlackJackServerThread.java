import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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
            response.put("playerHand", game.getPlayer().getHand().getHandOfCards());
            response.put("dealerHand", game.getDealer().getHand().getHandOfCards());
            response.put("message", "Game started. Please place your bet.");
            out.println(response.toJSONString());

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                JSONObject request = (JSONObject) new org.json.simple.parser.JSONParser().parse(inputLine);
                String action = (String) request.get("action");

                // Example action handling (to be expanded)
                if ("hit".equalsIgnoreCase(action)) {
                } else if ("stay".equalsIgnoreCase(action)) {
                }
                // Add logic for other actions and game state transitions

                // Update client with new game state
                response.clear();
                response.put("playerHand", game.getPlayer().getHand().getHandOfCards());
                response.put("dealerHand", game.getDealer().getHand().getHandOfCards());
                // Add more details to response as necessary
                out.println(response.toJSONString());

            }

            clientSocket.close();
        } catch (IOException | org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
    }
}