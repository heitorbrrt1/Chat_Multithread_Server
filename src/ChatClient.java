import java.io.*;
import java.net.*;

@SuppressWarnings({"resource", "CallToPrintStackTrace", "InfiniteLoopStatement"})
public class ChatClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("26.87.233.253", 12345);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Conectado ao servidor. \nDigite seu nome: ");
            String clientName = consoleIn.readLine();
            out.println(clientName);

            Thread receiverThread = new Thread(() -> {
                try {
                    String message;
                    while ((message = in.readLine()) != null) {
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            receiverThread.start();

            String input;
            while (true) {
                input = consoleIn.readLine();
                out.println(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
