package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.*;
import algorithms.search.BestFirstSearch;
import algorithms.search.BreadthFirstSearch;
import algorithms.search.DepthFirstSearch;

import java.io.*;
import java.util.Objects;

public class ServerStrategyGenerateMaze implements IServerStrategy{
    /** @implNote
     * 1. Read dimensions of requested maze from InStream<br>
     * 2. Get config data and choose appropriate mazeGenerator<br>
     * 3. Build maze<br>
     * 4. Compress byte[] of maze<br>
     * 5. Send compressed byte[] of maze through OutStream<br>
     * @param inFromClient A stream for receiving information from the client
     * @param outToClient A stream for sending information to the client
     */
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            int[] dim = (int[]) fromClient.readObject();
            IMazeGenerator gen;
            Configurations config = Configurations.getInstance();

            String algo = config.getConfig("server.mazeGeneratingAlgorithm");
            if(Objects.equals(algo, "MyMazeGenerator"))
            {
                gen = new MyMazeGenerator();
            } else if (Objects.equals(algo, "EmptyMazeGenerator")) {
                gen = new EmptyMazeGenerator();
            }
            else {//simple maze gen
                gen = new SimpleMazeGenerator();
            }
            Maze maze = gen.generate(dim[0],dim[1]);
            MyCompressorOutputStream compressorOutputStream = new MyCompressorOutputStream(new DataOutputStream(outToClient));
            byte[] arr = compressorOutputStream.compress(maze.toByteArray());
            toClient.writeObject(arr);
            toClient.flush();

            fromClient.close();
            toClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
