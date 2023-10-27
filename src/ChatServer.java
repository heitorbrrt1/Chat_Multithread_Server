import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatServer implements ChatServerInterface {
    private static final int PORT = 12345;
    private List<ClientHandler> clients = new ArrayList<>();
    private Map<String, Integer> voteCounts = new HashMap<>();
    public static void main(String[] args) {
        ChatServer server = new ChatServer();
        server.start();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor de Chat iniciado na porta " + PORT);

            //noinspection InfiniteLoopStatement
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Novo cliente conectado!");

                ClientHandler client = new ClientHandler(clientSocket, this);
                clients.add(client);
                client.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void broadcastMessage(String message, ClientHandler sender) throws IOException {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }
    public synchronized Map<String, Integer> getEnqueteResults() {
        return new HashMap<>(voteCounts); // Retorna uma cópia das contagens de votos
    }
    // Função para registrar e contabilizar votos
    public synchronized void recordVote(String option, ClientHandler voter) {
        String voterName = voter.getClientName();
        voteCounts.put(option, voteCounts.getOrDefault(option, 0) + 1);
        System.out.println(voterName + " votou em: " + option);
        // Você pode adicionar lógica adicional aqui, como notificar os clientes sobre os votos contabilizados.
    }

    public synchronized void removeClient(ClientHandler client) {
        String clientName = client.getClientName();
        clients.remove(client);

        try {
            // Enviar uma mensagem informando a desconexão do cliente
            String disconnectMessage = "Cliente '" + clientName + "' se desconectou.";
            System.out.println(disconnectMessage);
            broadcastMessage(disconnectMessage, null);
        } catch (IOException e) {
            // Trate a exceção aqui com uma mensagem personalizada
            System.err.println("Erro ao informar a desconexão do cliente: " + e.getMessage());
        }
    }

    @Override
    public synchronized void sendPrivateMessage(String recipient, String message, ClientHandler sender) throws IOException {
        for (ClientHandler client : clients) {
            if (client != sender && client.getClientName().equals(recipient)) {
                // Envie a mensagem privada com o formato <msg>mensagem<msg/>
                client.sendMessage(sender.getClientName() + " (privado para você): " + message);
                sender.sendMessage("(privado para " + recipient + "): " + message);
                return; // Enviamos a mensagem apenas para o destinatário
            }
        }
        sender.sendMessage("Destinatário não encontrado ou offline: " + recipient);
    }
    @Override
    public void broadcastEnquete(String question, String[] options, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage("Enquete: " + question);
                client.sendMessage("Opções: " + String.join(", ", options));
            }
        }
    }
}
