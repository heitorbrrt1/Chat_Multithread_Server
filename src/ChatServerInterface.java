import java.io.IOException;

public interface ChatServerInterface {
    void broadcastMessage(String message, ClientHandler sender) throws IOException;

    void removeClient(ClientHandler clientHandler);

    void sendPrivateMessage(String recipient, String msgContent, ClientHandler clientHandler) throws IOException;
}
