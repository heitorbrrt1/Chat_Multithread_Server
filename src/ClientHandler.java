import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientHandler extends Thread {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String clientName;
    private ChatServerInterface server;
    boolean votacaoEncerrada = false;
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
            clientName = in.readLine();

            // Avisar a todos que um novo cliente se conectou
            server.broadcastMessage(clientName + " entrou na sala.", this);

            String message;
            while (true) {
                message = in.readLine();
                if (message == null) {
                    break;
                }

                if (containsEmojiTag(message)) {
                    // É uma mensagem com emoji
                    String emojiName = extractEmojiName(message);
                    if (emojiName != null) {
                        // Obtenha o emoji com base no nome (por exemplo, usando a função getEmoji)
                        String emoji = getEmoji(emojiName);
                        if (emoji != null) {
                            // Substitua a tag do emoji pelo emoji real no conteúdo da mensagem
                            message = message.replace("<emoji:" + emojiName + ">", emoji);
                        }
                    }
                }
                if (isEnqueteMessage(message)) {
                    // Mensagem de início de enquete
                    votacaoEncerrada=false;
                    String enqueteContent = message.substring("<enquete>".length(), message.indexOf("<enquete/>"));
                    String[] enqueteData = enqueteContent.split(";");
                    String question = enqueteData[0];
                    String[] options = Arrays.copyOfRange(enqueteData, 1, enqueteData.length);
                    server.broadcastEnquete(question, options, this);
                } else if (isVoteMessage(message)) {
                    // Mensagem de voto
                    if(votacaoEncerrada){
                        sendMessage("Votacao encerrada!");
                    }else {
                        String option = extractVoteOption(message);
                        server.recordVote(option, this);
                    }

                } else if (isEndVoteMessage(message)) {
                    // Mensagem de finalização de votação
                     votacaoEncerrada = true;
                    Map<String, Integer> enqueteResults = server.getEnqueteResults();
                    sendMessage("Resultado da enquete:");
                    for (Map.Entry<String, Integer> entry : enqueteResults.entrySet()) {
                        sendMessage(entry.getKey() + ": " + entry.getValue() + " votos");
                    }
                }else if (isPrivateMessage(message)) {
                    // Mensagem privada
                    String recipient = extractRecipient(message);
                    String msgContent = extractMessageContent(message);
                    server.sendPrivateMessage(recipient, msgContent, this);
                } else if (isValidMessage(message)) {
                    // Mensagem pública
                    String msgContent = extractMessageContent(message);
                    server.broadcastMessage(clientName + ": " + msgContent, this);
                } else {
                    sendMessage("Mensagem inválida. As mensagens públicas devem estar no formato <msg>mensagem<msg/> e as mensagens privadas devem estar no formato <private>destinatário</private><msg>mensagem<msg/>.");
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

    private String getEmoji(String emojiName) {
        switch (emojiName) {
            case "smile":
                return "😀"; // Emoji de sorriso
            case "heart":
                return "❤️"; // Emoji de coração
            case "sad":
                return "😢"; // Emoji de cara triste
            case "thumbsup":
                return "👍"; // Emoji de polegar para cima
            case "thumbsdown":
                return "👎"; // Emoji de polegar para baixo
            case "clap":
                return "👏"; // Emoji de aplausos
            case "fire":
                return "🔥"; // Emoji de fogo
            // Adicione mais casos para outros emojis, se necessário
            default:
                return null; // Retorne null se o emoji não for reconhecido
        }
    }
    private boolean isEndVoteMessage(String message) {
        return message.equals("<endvote>");
    }

    private boolean isVoteMessage(String message) {
        return message.startsWith("<vote:") && message.endsWith(">");
    }
    private String extractVoteOption(String message) {
        int startIndex = "<vote:".length();
        int endIndex = message.length() - 1;
        return message.substring(startIndex, endIndex);
    }

    public void sendMessage(String message) {
        out.println(message);
    }
    private boolean isEnqueteMessage(String message) {
        return message.startsWith("<enquete>") && message.endsWith("<enquete/>");
    }
    // Extrai os dados da enquete (pergunta e opções) da mensagem
    private String[] extractEnqueteData(String message) {
        String enqueteContent = message.substring("<enquete>".length(), message.indexOf("<enquete/>"));
        return enqueteContent.split(";");
    }
    // Verifica se a mensagem é privada
    private boolean isPrivateMessage(String message) {
        return message.startsWith("<private>") && message.contains("<private/>");
    }

    // Extrai o nome do destinatário de uma mensagem privada
    private String extractRecipient(String message) {
        int start = message.indexOf("<private>") + "<private>".length();
        int end = message.indexOf("<private/>");
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
    // Verifica se a mensagem contém uma tag de emoji
    private boolean containsEmojiTag(String message) {
        return message.matches(".*<emoji:[a-zA-Z]+>.*");
    }


    private String replaceEmojiTagWithEmoji(String message, String emoji) {
        return message.replaceAll("<emoji:[a-zA-Z]+>", emoji);
    }

    // Extrai o nome do emoji da mensagem
    private String extractEmojiName(String message) {
        // Use uma expressão regular para encontrar a tag do emoji
        // e extrair o nome do emoji
        String regex = "<emoji:([a-zA-Z]+)>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            return matcher.group(1); // Captura somente o nome do emoji
        }
        return null; // Retorna null se nenhum nome de emoji for encontrado
    }

}
