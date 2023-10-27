import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public interface ChatServerInterface {
    void broadcastMessage(String message, ClientHandler sender) throws IOException;

    void removeClient(ClientHandler clientHandler);

    void sendPrivateMessage(String recipient, String msgContent, ClientHandler clientHandler) throws IOException;

    void broadcastEnquete(String question, String[] options, ClientHandler clientHandler);

    void recordVote(String option, ClientHandler voter);

    Map<String, Integer> getEnqueteResults();

    List<String> getConnectedUserNames(ClientHandler clientHandler);

    void sendHug(String hugRecipient, String clientName,ClientHandler clientHandler);
    void sendKiss(String hugRecipient, String clientName,ClientHandler clientHandler);
    void sendHandshake(String hugRecipient, String clientName,ClientHandler clientHandler);
}
