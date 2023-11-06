import java.io.*;
import java.net.*;

@SuppressWarnings({"resource", "CallToPrintStackTrace", "InfiniteLoopStatement"})
public class ChatClient {
    public static void main(String[] args) {
        try {
            // Estabelece uma conexão de soquete com o endereço IP "26.87.233.253" na porta 12345.
            Socket socket = new Socket("26.87.233.253", 12345);

            // Cria um leitor de entrada para receber mensagens do servidor.
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Cria um escritor de saída para enviar mensagens para o servidor.
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Cria um leitor de entrada para ler as entradas do usuário a partir da linha de comando.
            BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));

            // Exibe uma mensagem de boas-vindas e instruções para o usuário.
            System.out.println("Conectado ao servidor. \n<help> Para mostrar as tags \nDigite seu nome: ");

            // Lê o nome do cliente a partir da entrada do usuário e envia para o servidor.
            String clientName = consoleIn.readLine();
            out.println(clientName);

            // Cria uma thread separada para receber mensagens do servidor.
            Thread receiverThread = new Thread(() -> {
                try {
                    String message;
                    // Enquanto houver mensagens do servidor, imprime na tela do cliente.
                    while ((message = in.readLine()) != null) {
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    // Em caso de erro de E/S, imprime o rastreamento de pilha.
                    e.printStackTrace();
                }
            });

            // Inicia a thread de recebimento de mensagens.
            receiverThread.start();

            String input;
            // Entra em um loop infinito para permitir que o cliente envie mensagens continuamente.
            while (true) {
                input = consoleIn.readLine();
                out.println(input);
            }
        } catch (IOException e) {
            // Em caso de erro de E/S ao criar soquete ou fluxos de entrada/saída,
            // imprime o rastreamento de pilha.
            e.printStackTrace();
        }
    }
}
