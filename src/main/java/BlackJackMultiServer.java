import java.io.IOException;
import java.net.ServerSocket;

// BlackJackGameManager

public class BlackJackMultiServer {
    public static void main(String[] args) throws IOException {

        int portNumber = 4400;
        boolean listening = true;

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (listening) {
                new BlackJackServerThread(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }
}