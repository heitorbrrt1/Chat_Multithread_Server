import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"CallToPrintStackTrace", "FieldMayBeFinal", "InfiniteLoopStatement"})
public class ChatServer implements ChatServerInterface {
    // Porta na qual o servidor de chat irá escutar
    private static final int PORT = 12345;

    // Lista de manipuladores de cliente conectados
    private List<ClientHandler> clients = new ArrayList<>();

    // Mapa para rastrear a contagem de votos em uma enquete
    private Map<String, Integer> voteCounts = new HashMap<>();

    public static void main(String[] args) {
        // Inicializa o servidor de chat
        ChatServer server = new ChatServer();
        server.start();
    }

    // Inicializa o servidor e aguarda conexões de clientes
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            // Imprime uma mensagem ao iniciar o servidor
            System.out.println("Servidor de Chat iniciado na porta " + PORT);

            while (true) {
                // Aguarda a conexão de um novo cliente
                Socket clientSocket = serverSocket.accept();
                System.out.println("Novo cliente conectado!");

                // Cria um manipulador de cliente para o cliente recém-conectado
                ClientHandler client = new ClientHandler(clientSocket, this);
                clients.add(client);
                client.start();
            }
        } catch (IOException e) {
            // Em caso de exceção de E/S, imprime o rastreamento da pilha
            e.printStackTrace();
        }
    }

    // Implementação do método da interface para enviar uma mensagem de broadcast
    @Override
    public synchronized void broadcastMessage(String message, ClientHandler sender) throws IOException {
        // Envia uma mensagem para todos os clientes conectados, exceto o remetente
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }

    // Obtém os resultados da enquete
    public synchronized Map<String, Integer> getEnqueteResults() {
        return new HashMap<>(voteCounts);
    }

    // Registra o voto de um cliente em uma opção da enquete
    public synchronized void recordVote(String option, ClientHandler voter) {
        String voterName = voter.getClientName();
        voteCounts.put(option, voteCounts.getOrDefault(option, 0) + 1);
        System.out.println(voterName + " votou em: " + option);
    }

    // Remove um cliente da lista de clientes conectados
    public synchronized void removeClient(ClientHandler client) {
        String clientName = client.getClientName();
        clients.remove(client);

        try {
            // Informa a desconexão do cliente aos outros clientes
            String disconnectMessage = "Cliente '" + clientName + "' se desconectou.";
            System.out.println(disconnectMessage);
            broadcastMessage(disconnectMessage, null);
        } catch (IOException e) {
            // Em caso de exceção de E/S, imprime uma mensagem de erro
            System.err.println("Erro ao informar a desconexão do cliente: " + e.getMessage());
        }
    }

    // Implementação do método da interface para enviar uma mensagem privada
    @Override
    public synchronized void sendPrivateMessage(String recipient, String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender && client.getClientName().equals(recipient)) {
                // Envia mensagens privadas para o remetente e o destinatário
                client.sendMessage(sender.getClientName() + " (privado para você): " + message);
                sender.sendMessage("(privado para " + recipient + "): " + message);
                return;
            }
        }
    }

    // Implementação do método da interface para enviar uma enquete aos clientes
    @Override
    public void broadcastEnquete(String question, String[] options, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                // Envia informações sobre a enquete para todos os clientes
                client.sendMessage("Enquete: " + question);
                client.sendMessage("Opções: " + String.join(", ", options));
            }
        }
    }

    // Métodos para enviar saudações personalizadas
    public synchronized void sendHug(String recipient, String senderName, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client.getClientName().equals(recipient)) {
                // Envia um abraço para o destinatário
                client.sendMessage(senderName + " mandou um abraço para você!");
                return;
            }
        }
        sender.sendMessage("Destinatário não encontrado ou offline: " + recipient);
    }

    public synchronized void sendKiss(String recipient, String senderName, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client.getClientName().equals(recipient)) {
                // Envia um beijo para o destinatário
                client.sendMessage(senderName + " mandou um beijo para você!");
                return;
            }
        }
        sender.sendMessage("Destinatário não encontrado ou offline: " + recipient);
    }

    public synchronized void sendHandshake(String recipient, String senderName, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client.getClientName().equals(recipient)) {
                // Envia um aperto de mão para o destinatário
                client.sendMessage(senderName + " apertou sua mão!");
                return;
            }
        }
        sender.sendMessage("Destinatário não encontrado ou offline: " + recipient);
    }

    // Obtém os nomes de usuário dos clientes conectados
    public List<String> getConnectedUserNames(ClientHandler sender) {
        List<String> userNames = new ArrayList<>();
        for (ClientHandler client : clients) {
            if (client != sender) {
                userNames.add(client.getClientName());
            }
        }
        return userNames;
    }
}
