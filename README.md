# Java Web Server

Um servidor HTTP simples implementado em Java que responde requisições com arquivos HTML.

## Funcionalidades

- Servidor multithreaded para atender múltiplas conexões
- Responde com arquivos HTML do diretório atual
- Suporta códigos de status HTTP (200, 400, 404)
- Página inicial definida como `index.html`

## Como usar

1. Compile o arquivo Java:
   ```
   javac SocketServer.java
   ```

2. Execute o servidor especificando a porta:
   ```
   java SocketServer 8080
   ```

3. Acesse pelo navegador:
   ```
   http://localhost:8080/
   ```

## Notas importantes

- O servidor serve apenas arquivos HTML
- Requisições para outros tipos de arquivos retornam erro 400
- Arquivos inexistentes retornam erro 404
- `index.html` é carregado quando a rota raiz (/) é acessada

## Estrutura do código

- `main()`: Inicializa o servidor e aceita conexões
- `run()`: Processa requisições dos clientes em threads separadas
- `getClientRequestPath()`: Extrai o caminho solicitado pelo cliente
- `writeStatusAndHeaders()`: Escreve cabeçalhos HTTP na resposta
