import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
  public static void main(String args[]) {
    // Cria o servidor
    try (ServerSocket server = new ServerSocket(Integer.parseInt(args[0]))) {
      if (args.length < 1) {
        System.out.print("É necessário passar o número da porta.");
        return;
      }
      
      System.out.println("Servidor escutando na porta: " + args[0]);

      while (true) {
        // Espera o servidor receber uma conexão do cliente.
        Socket client = server.accept();

        System.out.println("Conexão estabelecida com: " + client.getLocalPort());

        // Recebendo o caminho que o cliente digitou
        String requestedPath = getClientRequestPath(client);

        String currentDirectory = System.getProperty("user.dir");

        File file = new File(currentDirectory + requestedPath);
        PrintWriter out = new PrintWriter(client.getOutputStream(), true);

        if (file.exists() && file.isFile()) {
          String fileName = file.getName();

          int i = fileName.lastIndexOf('.');
          if (!(fileName.substring(i + 1).equals(".html"))) {
            writeStatusAndHeaders(out, "400 Bad Request");
            out.println("Arquivo não é um html");
            out.close();
            client.close();
          }

          writeStatusAndHeaders(out, "200 OK");

          BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
          String line;
          while ((line = fileReader.readLine()) != null) {
            out.print(line);
          }
          fileReader.close();
        } else {
          writeStatusAndHeaders(out, "404 Not Found");
          out.println("Caminho inválido");
        }

        out.close();
        client.close();
        
      }

    } catch (IOException e) {
      System.out.println("Houve um erro ao iniciar o servidor.");
    }
  }

  public static String getClientRequestPath(Socket client) throws IOException {
    String requestedPath = "";
    InputStream clientInput = client.getInputStream();

    BufferedReader reader = new BufferedReader(new InputStreamReader(clientInput));
    String requestLine = reader.readLine();

    if (requestLine != null) {
      String[] requestParts = requestLine.split(" ");

      if (requestParts.length > 1)
        requestedPath = requestParts[1];
    }
    return requestedPath;
  }

  public static void writeStatusAndHeaders(PrintWriter out, String status) {
    out.println("HTTP/1.1 " + status);
    out.println("Content-Type: text/html; charset=UTF-8");
    out.println("Connection: close");
    out.println();
  }

}
