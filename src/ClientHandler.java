import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
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
    private boolean connected;
    private boolean valid=true;
    public ClientHandler(Socket socket, ChatServerInterface server) {
        this.socket = socket;
        this.server = server;
        this.connected=true;
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
            while (connected) {
                message = in.readLine();
                if (message == null) {
                    break;
                }
                if(isQuitMessage(message)){
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

                if (message.matches(".*<red>.*<red/>.*")) {
                    String startTag = "<red>";
                    String endTag = "<red/>";
                    int startIndex = message.indexOf(startTag);
                    int endIndex = message.indexOf(endTag);
                    if (startIndex != -1 && endIndex != -1) {
                        String textBeforeRed = message.substring(0, startIndex);
                        String textAfterRed = message.substring(endIndex + endTag.length());
                        String redText = "\u001B[31m" + message.substring(startIndex + startTag.length(), endIndex) + "\u001B[0m";
                        message = textBeforeRed + redText + textAfterRed;
                    }
                }
                if (message.matches(".*<blue>.*<blue/>.*")) {
                    // Encontre a tag <blue> e <blue/> na mensagem
                    String startTag = "<blue>";
                    String endTag = "<blue/>";

                    int startIndex = message.indexOf(startTag);
                    int endIndex = message.indexOf(endTag);

                    if (startIndex != -1 && endIndex != -1) {
                        // Substitua a tag pela formatação ANSI para azul apenas na parte desejada
                        String textBeforeBlue = message.substring(0, startIndex);
                        String textAfterBlue = message.substring(endIndex + endTag.length());

                        String blueText = "\u001B[34m" + message.substring(startIndex + startTag.length(), endIndex) + "\u001B[0m";

                        // Reconstrua a mensagem com a formatação de cor azul apenas na parte desejada
                        message = textBeforeBlue + blueText + textAfterBlue;
                    }
                }

                if (message.matches(".*<green>.*<green/>.*")) {
                    // Encontre a tag <green> e <green/> na mensagem
                    String startTag = "<green>";
                    String endTag = "<green/>";

                    int startIndex = message.indexOf(startTag);
                    int endIndex = message.indexOf(endTag);

                    if (startIndex != -1 && endIndex != -1) {
                        // Substitua a tag pela formatação ANSI para verde apenas na parte desejada
                        String textBeforeGreen = message.substring(0, startIndex);
                        String textAfterGreen = message.substring(endIndex + endTag.length());

                        String greenText = "\u001B[32m" + message.substring(startIndex + startTag.length(), endIndex) + "\u001B[0m";

                        // Reconstrua a mensagem com a formatação de cor verde apenas na parte desejada
                        message = textBeforeGreen + greenText + textAfterGreen;
                    }
                }
                if (message.matches(".*<yellow>.*<yellow/>.*")) {
                    String startTag = "<yellow>";
                    String endTag = "<yellow/>";

                    int startIndex = message.indexOf(startTag);
                    int endIndex = message.indexOf(endTag);

                    if (startIndex != -1 && endIndex != -1) {
                        String textBeforeYellow = message.substring(0, startIndex);
                        String textAfterYellow = message.substring(endIndex + endTag.length());

                        String yellowText = "\u001B[33m" + message.substring(startIndex + startTag.length(), endIndex) + "\u001B[0m";

                        message = textBeforeYellow + yellowText + textAfterYellow;
                    }
                }

                if (message.matches(".*<purple>.*<purple/>.*")) {
                    String startTag = "<purple>";
                    String endTag = "<purple/>";

                    int startIndex = message.indexOf(startTag);
                    int endIndex = message.indexOf(endTag);

                    if (startIndex != -1 && endIndex != -1) {
                        String textBeforePurple = message.substring(0, startIndex);
                        String textAfterPurple = message.substring(endIndex + endTag.length());

                        String purpleText = "\u001B[35m" + message.substring(startIndex + startTag.length(), endIndex) + "\u001B[0m";

                        message = textBeforePurple + purpleText + textAfterPurple;
                    }
                }
                if (message.matches(".*<orange>.*<orange/>.*")) {
                    String startTag = "<orange>";
                    String endTag = "<orange/>";

                    int startIndex = message.indexOf(startTag);
                    int endIndex = message.indexOf(endTag);

                    if (startIndex != -1 && endIndex != -1) {
                        String textBeforeOrange = message.substring(0, startIndex);
                        String textAfterOrange = message.substring(endIndex + endTag.length());

                        String orangeText = "\u001B[38;5;202m" + message.substring(startIndex + startTag.length(), endIndex) + "\u001B[0m";

                        message = textBeforeOrange + orangeText + textAfterOrange;
                    }
                }

                if (message.matches(".*<brown>.*<brown/>.*")) {
                    String startTag = "<brown>";
                    String endTag = "<brown/>";

                    int startIndex = message.indexOf(startTag);
                    int endIndex = message.indexOf(endTag);

                    if (startIndex != -1 && endIndex != -1) {
                        String textBeforeBrown = message.substring(0, startIndex);
                        String textAfterBrown = message.substring(endIndex + endTag.length());

                        String brownText = "\u001B[38;5;94m" + message.substring(startIndex + startTag.length(), endIndex) + "\u001B[0m";

                        message = textBeforeBrown + brownText + textAfterBrown;
                    }
                }
                if (message.matches(".*<cyan>.*<cyan/>.*")) {
                    String startTag = "<cyan>";
                    String endTag = "<cyan/>";

                    int startIndex = message.indexOf(startTag);
                    int endIndex = message.indexOf(endTag);

                    if (startIndex != -1 && endIndex != -1) {
                        String textBeforeCyan = message.substring(0, startIndex);
                        String textAfterCyan = message.substring(endIndex + endTag.length());

                        String cyanText = "\u001B[36m" + message.substring(startIndex + startTag.length(), endIndex) + "\u001B[0m";

                        message = textBeforeCyan + cyanText + textAfterCyan;
                    }
                }



                if (isPrivateMessage(message)) {
                    // Mensagem privada
                    String recipient = extractRecipient(message);
                    String msgContent = extractMessageContent(message);
                    server.sendPrivateMessage(recipient, msgContent, this);
                }else if (isValidMessage(message)) {
                    // Mensagem pública

                    String msgContent = extractMessageContent(message);
                    server.broadcastMessage(clientName + ": " + msgContent, this);
                } else {
                    valid=false;
                }
                if (isRenameMessage(message)) {
                    String startTag = "<rename:";
                    String endTag = ">";
                    int startIndex = message.indexOf(startTag);
                    int endIndex = message.indexOf(endTag);

                    if (startIndex != -1 && endIndex != -1) {
                        String newName = message.substring(startIndex + startTag.length(), endIndex);
                        String textAfterRename = message.substring(endIndex + 1);
                        String oldName = clientName;

                        // Altere o nome do cliente
                        clientName = newName;

                        // Avisar aos outros clientes que o nome foi alterado
                        server.broadcastMessage(oldName + " agora é " + newName, this);

                        // Remova a parte da tag <rename:nome> da mensagem
                        message = textAfterRename;
                        valid=true;
                    }
                }
                if (isUsersMessage(message)) {
                    // Mensagem de solicitação de lista de usuários
                    valid=true;
                    List<String> connectedUserNames = server.getConnectedUserNames(this);
                    sendMessage("Usuários conectados: " + String.join(", ", connectedUserNames));
                }
                if (isHelpMessage(message)) {
                    // Mensagem de ajuda
                    sendMessage("Para enviar uma mensagem: <msg>'conteudo'<msg/>");
                    sendMessage("Para enviar uma mensagem privada: <private>'usuario'<private/>");
                    sendMessage("Para adicionar um emoji na mensagem: <emoji:'nomedoemoji'>");
                    sendMessage("Para iniciar uma enquete: <enquete>pergunta;opção1;opção2;opção n<enquete/>");
                    sendMessage("Para votar em uma enquete: <vote:'opção'>");
                    sendMessage("Para encerrar a votação: <endvote>");
                    sendMessage("Para escrever em vermelho: <red>'conteudo'<red/>");
                    sendMessage("Para escrever em azul: <blue>'conteudo'<blue/>");
                    sendMessage("Para escrever em verde: <green>'conteudo'<green/>");
                    sendMessage("Para escrever em amarelo: <yellow>'conteudo'<yellow/>");
                    sendMessage("Para escrever em roxo: <purple>'conteudo'<purple/>");
                    sendMessage("Para escrever em laranja: <orange>'conteudo'<orange/>");
                    sendMessage("Para escrever em marrom: <brown>'conteudo'<brown/>");
                    sendMessage("Para escrever em ciano: <cyan>'conteudo'<cyan/>");
                    sendMessage("Para se desconectar: <quit>");
                    sendMessage("Para trocar de nome: <rename:'conteudo'>");
                    sendMessage("Para mostrar os usuarios conectados: <users>");

                    valid=true;
                }
                if (isHugMessage(message)) {
                    // Extrai o nome do cliente que deve receber o abraço
                    valid=true;
                    String hugRecipient = message.substring(message.indexOf(":") + 1, message.indexOf(">"));

                    // Envia o abraço
                    server.sendHug(hugRecipient, clientName,this);
                }
                if (isKissMessage(message)) {
                    // Extrai o nome do cliente que deve receber o abraço
                    valid=true;
                    String kissRecipient = message.substring(message.indexOf(":") + 1, message.indexOf(">"));

                    // Envia o abraço
                    server.sendKiss(kissRecipient, clientName,this);
                }
                if (isHandshakeMessage(message)) {
                    // Extrai o nome do cliente que deve receber o abraço
                    valid=true;
                    String handshakeRecipient = message.substring(message.indexOf(":") + 1, message.indexOf(">"));

                    // Envia o abraço
                    server.sendHandshake(handshakeRecipient, clientName,this);
                }

                if (isEnqueteMessage(message)) {
                    // Mensagem de início de enquete
                    votacaoEncerrada=false;
                    String enqueteContent = message.substring("<enquete>".length(), message.indexOf("<enquete/>"));
                    String[] enqueteData = enqueteContent.split(";");
                    String question = enqueteData[0];
                    String[] options = Arrays.copyOfRange(enqueteData, 1, enqueteData.length);
                    server.broadcastEnquete(question, options, this);
                    valid=true;
                } else if (isVoteMessage(message)) {
                    // Mensagem de voto
                    valid=true;
                    if(votacaoEncerrada){
                        sendMessage("Votacao encerrada!");
                    }else {
                        String option = extractVoteOption(message);
                        server.recordVote(option, this);
                    }

                } else if (isEndVoteMessage(message)) {
                    // Mensagem de finalização de votação
                    valid=true;
                    votacaoEncerrada = true;
                    Map<String, Integer> enqueteResults = server.getEnqueteResults();
                    sendMessage("Resultado da enquete:");
                    for (Map.Entry<String, Integer> entry : enqueteResults.entrySet()) {
                        sendMessage(entry.getKey() + ": " + entry.getValue() + " votos");
                    }
                }
                if(valid==false){
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
    private boolean isKissMessage(String message) {
        return message.matches("<kiss:[^>]+>");
    }
    private boolean isHandshakeMessage(String message) {
        return message.matches("<handshake:[^>]+>");
    }
    private boolean isHugMessage(String message) {
        return message.matches("<hug:[^>]+>");
    }

    private boolean isUsersMessage(String message) {
        return message.equals("<users>");
    }
    private boolean isHelpMessage(String message) {
        return message.equals("<help>");
    }
    private boolean isRenameMessage(String message) {
        return message.matches(".*<rename:.*>.*");
    }
    private boolean isEndVoteMessage(String message) {
        return message.equals("<endvote>");
    }
    private boolean isQuitMessage(String message) {
        return message.equals("<quit>");
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
// 🫂😘🤝

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