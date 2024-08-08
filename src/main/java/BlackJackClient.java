import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * The BlackJackClient class represents a simple client for interacting with a
 * Blackjack game server. This client connects to the server, sends user commands,
 * and processes responses from the server.
 */
public class BlackJackClient {

    /**
     * The entry point of the BlackJackClient application. Establishes a connection to
     * the Blackjack server and handles communication with it.
     */
    public static void main(String[] args) throws IOException {
        String hostName = "localhost";
        int portNumber = 4400;

        try (
                Socket socket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
        ) {
            String fromServer;
            String fromUser;

            while ((fromServer = in.readLine()) != null) {
                System.out.println("Server: " + fromServer);

                fromUser = stdIn.readLine();
                if (fromUser != null) {
                    JSONObject jsonObject = BlackJackJSONObject.getObject(fromUser);
                    out.println(jsonObject.toJSONString());
                }
            }
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }
    }
}
