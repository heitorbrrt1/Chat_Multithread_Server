import java.io.IOException;
import java.util.Map;

public interface ChatServerInterface {
    void broadcastMessage(String message, ClientHandler sender) throws IOException;

    void removeClient(ClientHandler clientHandler);

    void sendPrivateMessage(String recipient, String msgContent, ClientHandler clientHandler) throws IOException;

    void broadcastEnquete(String question, String[] options, ClientHandler clientHandler);

    void recordVote(String option, ClientHandler voter);

    Map<String, Integer> getEnqueteResults();
}
