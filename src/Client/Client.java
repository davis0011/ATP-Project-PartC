package Client;

import algorithms.mazeGenerators.Maze;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Client is a class for passing problems to {@link Server.Server} and accepting responses.
 * Client has no implementation for its Strategy, it will be implemented on a case-by-case basis
 */
public class Client {

    private Maze maze = null;

    private InetAddress serverIP;
    private int serverPort;
    private IClientStrategy strategy;

    public Client(InetAddress serverIP, int serverPort, IClientStrategy strategy) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.strategy = strategy;
    }


    /**
     * A method for generating communication with {@link Server.Server}s, be it single thread of multithreaded
     */
    public void communicateWithServer() {
        clientThread();
    }

    /**
     * Wrapper function to let Client be multithreaded
     */
    private void clientThread(){
        try(Socket serverSocket = new Socket(serverIP, serverPort)){
            System.out.println("connected to server - IP = " + serverIP + ", Port = " + serverPort);
            strategy.clientStrategy(serverSocket.getInputStream(), serverSocket.getOutputStream());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

