package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A Server for communication with {@link Client.Client}s through {@link Socket}s that applies a {@link IServerStrategy}
 */
public class Server {
    private int port;
    private int listeningIntervalMS;
    private IServerStrategy strategy;
    private boolean stop;
    private ExecutorService threadPool;

    /**
     * @param port The port the server listens on
     * @param listeningIntervalMS the interval at which the socket times out
     * @param strategy the {@link IServerStrategy} that is applied to {@link Client.Client}s
     */
    public Server(int port, int listeningIntervalMS, IServerStrategy strategy) {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.strategy = strategy;
        Configurations config = Configurations.getInstance();
        this.threadPool = Executors.newFixedThreadPool(Integer.parseInt(config.getConfig("server.ThreadPoolSize")));
    }

    /**
     * A helper method for starting the server's predefined Strategy as a thread to allow for multiple clients
     */
    public void start(){
        new Thread(()->{startThread();}).start();
    }

    /**
     * The method for connection between {@link Server} and {@link Client.Client} and the application of {@link IServerStrategy}
     */
    private void startThread(){
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningIntervalMS);
            System.out.println("Starting server at port = " + port);
            while (!stop) {

                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client accepted: " + clientSocket.toString());

                    threadPool.submit(() -> {
                        try {
                            strategy.applyStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
                            clientSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    });
                } catch (SocketTimeoutException e){
                    System.out.println("Socket timeout");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * The method for shutting down the server and all its connections, waits for the {@link java.util.concurrent.ThreadPoolExecutor} to finish
     */
    public void stop(){
        try{
            Thread.sleep(100);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        threadPool.shutdown();
        stop = true;
    }
}
