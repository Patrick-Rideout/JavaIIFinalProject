package MultiExample;

import java.io.IOException;
import java.net.ServerSocket;

public class KKMultiServer {
    public static void main(String[] args) throws IOException {

        int portNumber = 4400; //Integer.parseInt(args[0]);
        boolean listening = true;

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (listening) {
                new KKMultiServerThread(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }
}