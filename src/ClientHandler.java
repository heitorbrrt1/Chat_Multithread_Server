import java.io.*;
import java.net.Socket;

@SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal", "CallToPrintStackTrace", "ThrowFromFinallyBlock"})
public class ClientHandler extends Thread {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String clientName;
    private ChatServerInterface server;

    public ClientHandler(Socket socket, ChatServerInterface server) {
        this.socket = socket;
        this.server = server;
    }

    public String getClientName() {
        return clientName;
    }

    public void run() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Solicitar e armazenar o nome do cliente
            out.println("Digite seu nome:");
            clientName = in.readLine();

            // Avisar a todos que um novo cliente se conectou
            server.broadcastMessage(clientName + " entrou na sala.", this);

            String message;
            while (true) {
                message = in.readLine();
                if (message == null) {
                    break;
                }
                if (isPrivateMessage(message)) {
                    // Mensagem privada
                    String recipient = extractRecipient(message);
                    String msgContent = extractMessageContent(message);
                    server.sendPrivateMessage(recipient, msgContent, this);
                } else if (isValidMessage(message)) {
                    // Mensagem pública
                    String msgContent = extractMessageContent(message);
                    server.broadcastMessage(msgContent, this);
                } else {
                    sendMessage("Mensagem inválida. As mensagens públicas devem estar no formato <msg>mensagem<msg/> e as mensagens privadas devem estar no formato <private>destinatário<private/><msg>mensagem<msg/>.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Remover o cliente da lista de clientes
            server.removeClient(this);
            // Avisar a todos que o cliente desconectou
            try {
                server.broadcastMessage(clientName + " saiu da sala.", this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    // Verifica se a mensagem é privada
    private boolean isPrivateMessage(String message) {
        return message.startsWith("<private>") && message.contains("<private/>");
    }

    // Extrai o nome do destinatário de uma mensagem privada
    private String extractRecipient(String message) {
        int start = message.indexOf("<private>") + "<private>".length();
        int end = message.indexOf("</private>");
        return message.substring(start, end);
    }

    // Extrai o conteúdo da mensagem
    private String extractMessageContent(String message) {
        int start = message.indexOf("<msg>") + "<msg>".length();
        int end = message.indexOf("<msg/>");
        return message.substring(start, end);
    }

    // Verifica se a mensagem está no formato correto
    private boolean isValidMessage(String message) {
        return message.startsWith("<msg>") && message.endsWith("<msg/>");
    }
}
