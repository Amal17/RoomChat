package chat;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author root
 */
public class Client {
    
    private static String host = "localhost";
    private static int portNumber = 4444;
    
    private String userName;
    private String serverHost;
    private int serverPort;
    private Scanner userInputScanner;
    
    private Client(String userName, String host, int portNumber){
        this.userName = userName;
        this.host = host;
        this.portNumber = portNumber;
    }
    
    private void startClient(Scanner scan){
        try {
            Socket socket = new Socket(host, portNumber);
            Thread.sleep(1000);
            
            ServerThread serverThread = new ServerThread(socket, userName);
            Thread serverAccessThread = new Thread(serverThread);
            serverAccessThread.start();
            
            while(serverAccessThread.isAlive()){
                if(scan.hasNextLine()){
                    serverThread.addNextMessage(scan.nextLine());
                }else{
                    Thread.sleep(2000);
                }
            }
        } catch (IOException e) {
            System.err.println("Fatal Connection error!");
            e.printStackTrace();
        } catch (InterruptedException e){
            System.out.println("Interrupted : "+e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        String readName = null;
        Scanner scan = new Scanner(System.in);
        
        System.out.println("Please input username: ");
        
        while(readName  == null || readName.trim().equals("")){
            readName = scan.nextLine();
            
            if (readName.trim().equals("")){
                System.out.println("Invalid. Please enter again:");
            }
        }
        
        Client client = new Client(readName, host, portNumber);
        client.startClient(scan);
    }
    
    
}
