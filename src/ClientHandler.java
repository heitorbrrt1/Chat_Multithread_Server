import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings({"FieldMayBeFinal", "FieldCanBeLocal", "CallToPrintStackTrace"})
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

    // O método run é executado quando uma instância de ClientHandler é iniciada como uma thread.
    public void run() {
        try {
            // Inicializa os fluxos de saída e entrada para comunicação com o cliente.
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Lê o nome do cliente a partir do fluxo de entrada.
            clientName = in.readLine();

            // Notifica o servidor que o cliente entrou na sala.
            server.broadcastMessage(clientName + " entrou na sala.", this);

            String message;
            while (connected) {
                // Lê as mensagens do cliente enquanto a conexão estiver ativa.
                message = in.readLine();

                // Verifica se a mensagem é nula, indicando que o cliente encerrou a conexão.
                if (message == null) {
                    break;
                }

                // Verifica se a mensagem é um comando de saída.
                if (isQuitMessage(message)) {
                    break;
                }

                // Verifica se a mensagem contém uma tag de emoji e a substitui pelo emoji correspondente.
                if (containsEmojiTag(message)) {
                    String emojiName = extractEmojiName(message);
                    if (emojiName != null) {
                        String emoji = getEmoji(emojiName);
                        if (emoji != null) {
                            message = message.replace("<emoji:" + emojiName + ">", emoji);
                        }
                    }
                }

                // Verifica se a mensagem contém uma tag de formatação para texto vermelho e a aplica.
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

                // Verifica se a mensagem contém uma tag de formatação para texto azul e a aplica.
                if (message.matches(".*<blue>.*<blue/>.*")) {
                    String startTag = "<blue>";
                    String endTag = "<blue/>";

                    int startIndex = message.indexOf(startTag);
                    int endIndex = message.indexOf(endTag);

                    if (startIndex != -1 && endIndex != -1) {
                        String textBeforeBlue = message.substring(0, startIndex);
                        String textAfterBlue = message.substring(endIndex + endTag.length());

                        String blueText = "\u001B[34m" + message.substring(startIndex + startTag.length(), endIndex) + "\u001B[0m";

                        message = textBeforeBlue + blueText + textAfterBlue;
                    }
                }

                // Verifica se a mensagem contém uma tag de formatação para texto verde e a aplica.
                if (message.matches(".*<green>.*<green/>.*")) {
                    String startTag = "<green>";
                    String endTag = "<green/>";

                    int startIndex = message.indexOf(startTag);
                    int endIndex = message.indexOf(endTag);

                    if (startIndex != -1 && endIndex != -1) {
                        String textBeforeGreen = message.substring(0, startIndex);
                        String textAfterGreen = message.substring(endIndex + endTag.length());

                        String greenText = "\u001B[32m" + message.substring(startIndex + startTag.length(), endIndex) + "\u001B[0m";

                        message = textBeforeGreen + greenText + textAfterGreen;
                    }
                }

                // Verifica se a mensagem contém uma tag de formatação para texto amarelp e a aplica.
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

                // Verifica se a mensagem contém uma tag de formatação para texto violeta e a aplica.
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
                // Verifica se a mensagem contém uma tag de formatação para texto laranja e a aplica.
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

                // Verifica se a mensagem contém uma tag de formatação para texto marrom e a aplica.
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

                // Verifica se a mensagem contém uma tag de formatação para texto ciano e a aplica.
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

                // Verifica se a mensagem é uma mensagem privada e a encaminha para o destinatário.
                if (isPrivateMessage(message)) {
                    String recipient = extractRecipient(message);
                    String msgContent = extractMessageContent(message);
                    server.sendPrivateMessage(recipient, msgContent, this);
                }else if (isValidMessage(message)) {
                    String msgContent = extractMessageContent(message);
                    server.broadcastMessage(clientName + ": " + msgContent, this);
                } else {
                    valid=false;
                }

                // Verifica se a mensagem é um comando de renomear o cliente.
                if (isRenameMessage(message)) {
                    String startTag = "<rename:";
                    String endTag = ">";
                    int startIndex = message.indexOf(startTag);
                    int endIndex = message.indexOf(endTag);

                    if (startIndex != -1 && endIndex != -1) {
                        String newName = message.substring(startIndex + startTag.length(), endIndex);
                        String textAfterRename = message.substring(endIndex + 1);
                        String oldName = clientName;

                        clientName = newName;

                        server.broadcastMessage(oldName + " agora é " + newName, this);

                        message = textAfterRename;
                        valid=true;
                    }
                }

                // Verifica se a mensagem é um comando para listar os usuários conectados.
                if (isUsersMessage(message)) {
                    valid=true;
                    List<String> connectedUserNames = server.getConnectedUserNames(this);
                    sendMessage("Usuários conectados: " + String.join(", ", connectedUserNames));
                }

                // Verifica se a mensagem é um comando de ajuda e envia informações de ajuda ao cliente.
                if (isHelpMessage(message)) {
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
                    sendMessage("Para enviar um abraço <hug:'nome'>");
                    sendMessage("Para enviar um aperto de mão <handshake:'nome'>");
                    sendMessage("Para enviar um beijo <kiss:'nome'>");

                    valid=true;
                }
                String substring = message.substring(message.indexOf(":") + 1, message.indexOf(">"));
                // Verifica se a mensagem é um comando para enviar um abraço
                if (isHugMessage(message)) {
                    valid=true;

                    server.sendHug(substring, clientName,this);
                }
                // Verifica se a mensagem é um comando para enviar um beijo
                if (isKissMessage(message)) {
                    valid=true;

                    server.sendKiss(substring, clientName,this);
                }
                // Verifica se a mensagem é um comando para enviar um aperto de mão
                if (isHandshakeMessage(message)) {
                    valid=true;

                    server.sendHandshake(substring, clientName,this);
                }
                // Verifica se a mensagem é um comando para iniciar uma enquete.
                if (isEnqueteMessage(message)) {
                    votacaoEncerrada=false;
                    String enqueteContent = message.substring("<enquete>".length(), message.indexOf("<enquete/>"));
                    String[] enqueteData = enqueteContent.split(";");
                    String question = enqueteData[0];
                    String[] options = Arrays.copyOfRange(enqueteData, 1, enqueteData.length);
                    server.broadcastEnquete(question, options, this);
                    valid=true;
                }
                // Se a mensagem for um voto em uma enquete, registra o voto.
                else if (isVoteMessage(message)) {
                    valid=true;
                    if(votacaoEncerrada){
                        sendMessage("Votacao encerrada!");
                    }else {
                        String option = extractVoteOption(message);
                        server.recordVote(option, this);
                    }

                }
                // Se a mensagem indica o encerramento de uma votação, exibe os resultados.
                else if (isEndVoteMessage(message)) {
                    valid=true;
                    votacaoEncerrada = true;
                    Map<String, Integer> enqueteResults = server.getEnqueteResults();
                    sendMessage("Resultado da enquete:");
                    for (Map.Entry<String, Integer> entry : enqueteResults.entrySet()) {
                        sendMessage(entry.getKey() + ": " + entry.getValue() + " votos");
                    }
                }
                // Se a mensagem não for válida, envia uma mensagem de erro ao cliente.
                if(!valid){
                    sendMessage("Mensagem inválida. As mensagens públicas devem estar no formato <msg>mensagem<msg/> \ne as mensagens privadas devem estar no formato <private>destinatário</private><msg>mensagem<msg/>.");

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // Fecha o soquete quando a conexão é encerrada.
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Notifica o servidor que o cliente foi removido.
            server.removeClient(this);

        }
    }

    // Método para obter um emoji com base no nome fornecido
    private String getEmoji(String emojiName) {
        return switch (emojiName) {
            case "smile" -> "😀";
            case "heart" -> "❤️";
            case "sad" -> "😢";
            case "thumbsup" -> "👍";
            case "thumbsdown" -> "👎";
            case "clap" -> "👏";
            case "fire" -> "🔥";
            default -> null;
        };
    }
    // Verifica se a mensagem é do tipo "beijo"
    private boolean isKissMessage(String message) {
        return message.matches("<kiss:[^>]+>");
    }

    // Verifica se a mensagem é do tipo "aperto de mão"
    private boolean isHandshakeMessage(String message) {
        return message.matches("<handshake:[^>]+>");
    }

    // Verifica se a mensagem é do tipo "abraço"
    private boolean isHugMessage(String message) {
        return message.matches("<hug:[^>]+>");
    }

    // Verifica se a mensagem é do tipo "usuários"
    private boolean isUsersMessage(String message) {
        return message.equals("<users>");
    }
    // Verifica se a mensagem é do tipo "ajuda"
    private boolean isHelpMessage(String message) {
        return message.equals("<help>");
    }

    // Verifica se a mensagem é do tipo "renomear"
    private boolean isRenameMessage(String message) {
        return message.matches(".*<rename:.*>.*");
    }

    // Verifica se a mensagem é do tipo "encerrar votação"
    private boolean isEndVoteMessage(String message) {
        return message.equals("<endvote>");
    }

    // Verifica se a mensagem é do tipo "sair"
    private boolean isQuitMessage(String message) {
        return message.equals("<quit>");
    }

    // Verifica se a mensagem é do tipo "votar"
    private boolean isVoteMessage(String message) {
        return message.startsWith("<vote:") && message.endsWith(">");
    }

    // Extrai a opção de voto de uma mensagem de voto
    private String extractVoteOption(String message) {
        int startIndex = "<vote:".length();
        int endIndex = message.length() - 1;
        return message.substring(startIndex, endIndex);
    }

    // Método para enviar uma mensagem para o cliente
    public void sendMessage(String message) {
        out.println(message);
    }

    // Verifica se a mensagem é do tipo "enquete"
    private boolean isEnqueteMessage(String message) {
        return message.startsWith("<enquete>") && message.endsWith("<enquete/>");
    }

    // Verifica se a mensagem é do tipo "mensagem privada"
    private boolean isPrivateMessage(String message) {
        return message.startsWith("<private>") && message.contains("<private/>");
    }

    // Extrai o destinatário de uma mensagem privada
    private String extractRecipient(String message) {
        int start = message.indexOf("<private>") + "<private>".length();
        int end = message.indexOf("<private/>");
        return message.substring(start, end);
    }

    // Extrai o conteúdo de uma mensagem
    private String extractMessageContent(String message) {
        int start = message.indexOf("<msg>") + "<msg>".length();
        int end = message.indexOf("<msg/>");
        return message.substring(start, end);
    }

    // Verifica se a mensagem é uma mensagem válida
    private boolean isValidMessage(String message) {
        return message.startsWith("<msg>") && message.endsWith("<msg/>");
    }

    // Verifica se a mensagem contém uma tag de emoji
    private boolean containsEmojiTag(String message) {
        return message.matches(".*<emoji:[a-zA-Z]+>.*");
    }

    // Extrai o nome do emoji de uma mensagem
    private String extractEmojiName(String message) {
        String regex = "<emoji:([a-zA-Z]+)>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

}
