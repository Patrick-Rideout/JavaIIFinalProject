package MultiExample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class KKMultiServerThread extends Thread {
    private Socket clientSocket = null;

    public KKMultiServerThread(Socket clientSocket) {
        super("KKMultiServerThread");
        this.clientSocket = clientSocket;
    }

    public void run() {

        try (
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                clientSocket.getInputStream()));
        ) {
            String inputLine, outputLine;
            KnockKnockProtocol kkp = new KnockKnockProtocol();
            outputLine = kkp.processInput(null);
            out.println(outputLine);

            while ((inputLine = in.readLine()) != null) {
                outputLine = kkp.processInput(inputLine);
                out.println(outputLine);
                if (outputLine.equals("Bye"))
                    break;
            }
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}