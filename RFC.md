# Protocolo de Chat Multithread

Este documento descreve um protocolo de chat multiusuário implementado em Java. Ele fornece informações sobre os comandos disponíveis, sua sintaxe e semântica no contexto do serviço.

## Autores

- Caio César
- Heitor Barreto

## Resumo

Este mini RFC descreve um protocolo de comunicação para um serviço de chat multiusuário, fornecendo as palavras-chave, sua sintaxe e semântica no contexto do serviço.

## Palavras-chave

As palavras-chave descritas neste documento são usadas para definir os comandos que os clientes e servidores podem trocar para interagir no serviço de chat.

## Comandos do Cliente

1. **<help>**
   - *Sintaxe*: `<help>`
   - *Semântica*: Solicita informações sobre as tags disponíveis e sua sintaxe.

2. **<quit>**
   - *Sintaxe*: `<quit>`
   - *Semântica*: Encerra a conexão com o servidor.

3. **<private>**
   - *Sintaxe*: `<private>'usuario'<private/><msg>'mensagem'<msg/>`
   - *Semântica*: Permite que um cliente envie uma mensagem privada para outro cliente identificado como 'usuario'.

4. **<rename>**
   - *Sintaxe*: `<rename:'novo_nome'>`
   - *Semântica*: Permite que um cliente altere seu nome de exibição para 'novo_nome'.

5. **<users>**
   - *Sintaxe*: `<users>`
   - *Semântica*: Solicita uma lista de nomes dos usuários conectados no servidor.

6. **<vote>**
   - *Sintaxe*: `<vote:'opção'>`
   - *Semântica*: Permite que um cliente vote em uma opção específica em uma enquete em andamento.

7. **<enquete>**
   - *Sintaxe*: `<enquete>'pergunta;opção1;opção2;opção n'<enquete/>`
   - *Semântica*: Permite que um cliente inicie uma enquete com uma pergunta e opções.

8. **<endvote>**
   - *Sintaxe*: `<endvote>`
   - *Semântica*: Encerra uma votação e exibe os resultados da enquete.

9. **<msg>**
   - *Sintaxe*: `<msg>'mensagem'<msg/>`
   - *Semântica*: Permite que um cliente envie uma mensagem pública para todos os outros clientes.

10. **<red>**
    - *Sintaxe*: `<red>'mensagem'<red/>`
    - *Semântica*: Permite que um cliente envie uma mensagem pública em vermelho.

11. **<blue>**
    - *Sintaxe*: `<blue>'mensagem'<blue/>`
    - *Semântica*: Permite que um cliente envie uma mensagem pública em azul.

12. **<green>**
    - *Sintaxe*: `<green>'mensagem'<green/>`
    - *Semântica*: Permite que um cliente envie uma mensagem pública em verde.

13. **<yellow>**
    - *Sintaxe*: `<yellow>'mensagem'<yellow/>`
    - *Semântica*: Permite que um cliente envie uma mensagem pública em amarelo.

14. **<purple>**
    - *Sintaxe*: `<purple>'mensagem'<purple/>`
    - *Semântica*: Permite que um cliente envie uma mensagem pública em roxo.

15. **<orange>**
    - *Sintaxe*: `<orange>'mensagem'<orange/>`
    - *Semântica*: Permite que um cliente envie uma mensagem pública em laranja.

16. **<brown>**
    - *Sintaxe*: `<brown>'mensagem'<brown/>`
    - *Semântica*: Permite que um cliente envie uma mensagem pública em marrom.

17. **<cyan>**
    - *Sintaxe*: `<cyan>'mensagem'<cyan/>`
    - *Semântica*: Permite que um cliente envie uma mensagem pública em ciano.

18. **<emoji>**
    - *Sintaxe*: `<emoji:'nomedoemoji'>`
    - *Semântica*: Permite que um cliente adicione um emoji na mensagem.

19. **<hug>**
    - *Sintaxe*: `<hug:'nome'>`
    - *Semântica*: Permite que um cliente envie um abraço para outro cliente pelo nome.

20. **<kiss>**
    - *Sintaxe*: `<kiss:'nome'>`
    - *Semântica*: Permite que um cliente envie um beijo para outro cliente pelo nome.

21. **<handshake>**
    - *Sintaxe*: `<handshake:'nome'>`
    - *Semântica*: Permite que um cliente envie um cumprimento (aperto de mão) para outro cliente pelo nome.


## Comandos do Servidor

Os comandos do servidor são ações que o servidor pode realizar em resposta aos comandos dos clientes.

1. **broadcastMessage**
   - *Sintaxe*: `broadcastMessage('mensagem', clienteRemetente)`
   - *Semântica*: Envia uma mensagem para todos os clientes conectados, exceto o remetente.

2. **removeClient**
   - *Sintaxe*: `removeClient(clienteRemovido)`
   - *Semântica*: Remove um cliente da lista de clientes ativos.

3. **sendPrivateMessage**
   - *Sintaxe*: `sendPrivateMessage('destinatário', 'mensagem', clienteRemetente)`
   - *Semântica*: Envia uma mensagem privada para um destinatário específico.

4. **broadcastEnquete**
   - *Sintaxe*: `broadcastEnquete('pergunta', ['opção1', 'opção2', ...], clienteRemetente)`
   - *Semântica*: Envia uma enquete com uma pergunta e opções para todos os clientes.

5. **recordVote**
   - *Sintaxe*: `recordVote('opção', clienteVotante)`
   - *Semântica*: Registra o voto de um cliente em uma enquete.

6. **getEnqueteResults**
   - *Sintaxe*: `getEnqueteResults()`
   - *Semântica*: Obtém os resultados da enquete, ou seja, a contagem de votos para cada opção.

7. **getConnectedUserNames**
   - *Sintaxe*: `getConnectedUserNames(clienteSolicitante)`
   - *Semântica*: Obtém os nomes dos usuários conectados, excluindo o cliente solicitante.

8. **sendHug**
   - *Sintaxe*: `sendHug('destinatário', 'nomeRemetente', clienteRemetente)`
   - *Semântica*: Envia um abraço (hug) para um destinatário específico.

9. **sendKiss**
   - *Sintaxe*: `sendKiss('destinatário', 'nomeRemetente', clienteRemetente)`
   - *Semântica*: Envia um beijo (kiss) para um destinatário específico.

10. **sendHandshake**
    - *Sintaxe*: `sendHandshake('destinatário', 'nomeRemetente', clienteRemetente)`
    - *Semântica*: Envia um cumprimento (handshake) para um destinatário específico.
