package chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author root
 */
public class ClientThread implements Runnable{
    
    private Socket socket;
    private PrintWriter clientOut;
    private Server server;
    
    ClientThread (Server server, Socket socket){
        this.server = server;
        this.socket = socket;
    }
    
    private PrintWriter getWriter(){
        return clientOut;
    }

    @Override
    public void run() {
        try {
            this.clientOut = new PrintWriter(socket.getOutputStream(), false);
            Scanner in = new Scanner(socket.getInputStream());
            
            while (socket.isConnected()) {
                if (in.hasNextLine()){
                    String input = in.nextLine();
//                    System.out.println(input);
                    for(ClientThread thatClient : server.getClients()){
                        PrintWriter thatClientOut = thatClient.getWriter();
                        if(thatClientOut != null){
                            thatClientOut.write(input+"\r\n");
                            thatClientOut.flush();
                        }
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
