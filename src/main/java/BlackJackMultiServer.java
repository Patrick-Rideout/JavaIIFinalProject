import java.io.IOException;
import java.net.ServerSocket;

/**
 * The BlackJackMultiServer class implements a multi-threaded server for managing
 * multiple Blackjack game sessions. It listens for incoming client connections
 * on a specified port and starts a new thread to handle each connection.
 */
public class BlackJackMultiServer {

    /**
     * The entry point for the BlackJackMultiServer application. This method sets up
     * the server to listen on a specific port for incoming client connections. For
     * each client connection, it creates and starts a new thread to handle the
     * communication with the client.
     *
     * @param args Command-line arguments (not used).
     * @throws IOException If an I/O error occurs when opening the socket or when
     * accepting a connection.
     */
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
