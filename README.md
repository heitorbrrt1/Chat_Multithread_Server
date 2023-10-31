# Protocolo de Chat Multiusuário

Este documento descreve um protocolo de chat multiusuário implementado em Java. Ele fornece informações sobre os comandos disponíveis, sua sintaxe e semântica no contexto do serviço.

## Autores

- Caio César
- Heitor Barreto

## Data de Publicação

30 de janeiro de 2023

## Status deste Memo

Este documento especifica um protocolo de chat multiusuário implementado em Java.

## Resumo

Este mini RFC descreve um protocolo de comunicação para um serviço de chat multiusuário, fornecendo as palavras-chave, sua sintaxe e semântica no contexto do serviço.

## Palavras-chave

As palavras-chave descritas neste documento são usadas para definir os comandos que os clientes e servidores podem trocar para interagir no serviço de chat.

## Comandos do Cliente

### <help>

- Sintaxe: `<help>`
- Semântica: Solicita informações sobre as tags disponíveis e sua sintaxe.

### <quit>

- Sintaxe: `<quit>`
- Semântica: Encerra a conexão com o servidor.

### <private>

- Sintaxe: `<private>'usuario'<private/><msg>'mensagem'<msg/>`
- Semântica: Permite que um cliente envie uma mensagem privada para outro cliente identificado como 'usuario'.

### <rename>

- Sintaxe: `<rename:'novo_nome'>`
- Semântica: Permite que um cliente altere seu nome de exibição para 'novo_nome'.

### <users>

- Sintaxe: `<users>`
- Semântica: Solicita uma lista de nomes dos usuários conectados no servidor.

### <vote>

- Sintaxe: `<vote:'opção'>`
- Semântica: Permite que um cliente vote em uma opção específica em uma enquete em andamento.

### <enquete>

- Sintaxe: `<enquete>'pergunta;opção1;opção2;opção n'<enquete/>`
- Semântica: Permite que um cliente inicie uma enquete com uma pergunta e opções.

### <endvote>

- Sintaxe: `<endvote>`
- Semântica: Encerra uma votação e exibe os resultados da enquete.

### <msg>

- Sintaxe: `<msg>'mensagem'<msg/>`
- Semântica: Permite que um cliente envie uma mensagem pública para todos os outros clientes.

### <red>

- Sintaxe: `<red>'mensagem'<red/>`
- Semântica: Permite que um cliente envie uma mensagem pública em vermelho.

### <blue>

- Sintaxe: `<blue>'mensagem'<blue/>`
- Semântica: Permite que um cliente envie uma mensagem pública em azul.

### <green>

- Sintaxe: `<green>'mensagem'<green/>`
- Semântica: Permite que um cliente envie uma mensagem pública em verde.

### <yellow>

- Sintaxe: `<yellow>'mensagem'<yellow/>`
- Semântica: Permite que um cliente envie uma mensagem pública em amarelo.

### <purple>

- Sintaxe: `<purple>'mensagem'<purple/>`
- Semântica: Permite que um cliente envie uma mensagem pública em roxo.

### <orange>

- Sintaxe: `<orange>'mensagem'<orange/>`
- Semântica: Permite que um cliente envie uma mensagem pública em laranja.

### <brown>

- Sintaxe: `<brown>'mensagem'<brown/>`
- Semântica: Permite que um cliente envie uma mensagem pública em marrom.

### <cyan>

- Sintaxe: `<cyan>'mensagem'<cyan/>`
- Semântica: Permite que um cliente envie uma mensagem pública em ciano.

### <emoji>

- Sintaxe: `<emoji:'nomedoemoji'>`
- Semântica: Permite que um cliente adicione um emoji na mensagem.

### <hug>

- Sintaxe: `<hug:'nome'>`
- Semântica: Permite que um cliente envie um abraço para outro cliente pelo nome.

### <kiss>

- Sintaxe: `<kiss:'nome'>`
- Semântica: Permite que um cliente envie um beijo para outro cliente pelo nome.

### <handshake>

- Sintaxe: `<handshake:'nome'>`
- Semântica: Permite que um cliente envie um cumprimento (aperto de mão) para outro cliente pelo nome.

## Comandos do Servidor

Os comandos do servidor são ações que o servidor pode realizar em resposta aos comandos dos clientes.

### broadcastMessage

- Sintaxe: `broadcastMessage('mensagem', clienteRemetente)`
- Semântica: Envia uma mensagem para todos os clientes conectados, exceto o remetente.

### removeClient

- Sintaxe: `removeClient(clienteRemovido)`
- Semântica: Remove um cliente da lista de clientes ativos.

### sendPrivateMessage

- Sintaxe: `sendPrivateMessage('destinatário', 'mensagem', clienteRemetente)`
- Semântica: Envia uma mensagem privada para um destinatário específico.

### broadcastEnquete

- Sintaxe: `broadcastEnquete('pergunta', ['opção1', 'opção2', ...], clienteRemetente)`
- Semântica: Envia uma enquete com uma pergunta e opções para todos os clientes.

### recordVote

- Sintaxe: `recordVote('opção', clienteVotante)`
- Semântica: Registra o voto de um cliente em uma enquete.

### getEnqueteResults

- Sintaxe: `getEnqueteResults()`
- Semântica: Obtém os resultados da enquete, ou seja, a contagem de votos para cada opção.

### getConnectedUserNames

- Sintaxe: `getConnectedUserNames(clienteSolicitante)`
- Semântica: Obtém os nomes dos usuários conectados, excluindo o cliente solicitante.

### sendHug

- Sintaxe: `sendHug('destinatário', 'nomeRemetente', clienteRemetente)`
- Semântica: Envia um abraço (hug) para um destinatário específico.

### sendKiss

- Sintaxe: `sendKiss('destinatário', 'nomeRemetente', clienteRemetente)`
- Semântica: Envia um beijo (kiss) para um destinatário específico.

### sendHandshake

- Sintaxe: `sendHandshake('destinatário', 'nomeRemetente', clienteRemetente)`
- Semântica: Envia um cumprimento (handshake) para um destinatário específico.

## Exemplos de Uso

Para exemplos de uso dos comandos e suas respectivas sintaxes, consulte o código das classes fornecidas.

## Considerações Finais

Este mini RFC define um protocolo de chat multiusuário com palavras-chave, sintaxe e semântica bem definidas, permitindo a comunicação eficiente entre os clientes e o servidor. A implementação em Java demonstra a funcionalidade do protocolo.
