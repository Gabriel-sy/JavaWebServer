import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
  public static void main(String args[]) {
    try {
      if (args.length < 1){
        System.out.print("É necessário passar o número da porta.");
        return;
      }
      //Cria o servidor
      ServerSocket server = new ServerSocket(Integer.parseInt(args[0]));

      System.out.println("Servidor escutando na porta: " + args[0]);

      //Espera o servidor receber uma conexão do cliente.
      Socket client = server.accept();
      
      System.out.println("Conexão estabelecida com: " + client.getLocalPort());

      //Recebendo o que quer que o cliente escreveu para o servidor.
      PrintWriter out = new PrintWriter(client.getOutputStream(), true);
      out.println("HTTP/1.1 200 OK");
      out.println("Content-Type: text/plain; charset=UTF-8");
      out.println("Connection: close");
      out.println();

      out.close();
      client.close();
      server.close();

    } catch (Exception e) {
      e.printStackTrace();
    }
    }
    
  }
