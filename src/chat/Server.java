package chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author root
 */
public class Server {
    private static final int PORT_NUMBER = 4444;
    
    private final int SERVER_PORT;
    private List <ClientThread> clients;
    
    public static void main(String[] args) {
        Server server = new Server(PORT_NUMBER);
        server.startServer();
    }
    
    public Server(int portNumber){
        SERVER_PORT = portNumber; 
    }
    
    public List<ClientThread> getClients(){
        return clients;
    }
    
    private void startServer(){
        clients = new ArrayList<ClientThread>();
        ServerSocket serverSocket = null;
        
        try {
            serverSocket = new ServerSocket(SERVER_PORT);
            acceptClients(serverSocket);
        } catch (IOException e) {
            System.err.println("Port Error : "+e);
            System.exit(1);
        }
    }
    
    private void acceptClients(ServerSocket serverSocket){
        System.out.println("Server starts on port : "+serverSocket.getLocalSocketAddress());
        
        while (true) {            
            try {
                Socket socket = serverSocket.accept();
                System.out.println("Accepts : "+socket.getRemoteSocketAddress());
                ClientThread client = new ClientThread(this, socket);
                Thread thread = new Thread(client);
                thread.start();
                clients.add(client);
            } catch (IOException e) {
                System.err.println("Accept failed on: "+SERVER_PORT);
            }
        }
    }
}
