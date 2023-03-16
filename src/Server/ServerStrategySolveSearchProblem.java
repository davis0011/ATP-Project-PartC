package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Properties;

public class ServerStrategySolveSearchProblem implements IServerStrategy{
    /** @implNote
     * 1. Read maze object from InStream<br>
     * 2. Search temp directory for saved solution to maze by comparing hash codes<br>
     * 2.1. If exists match: send saved Solution to OutStream and return<br>
     * 3. Solve maze<br>
     * 4. Save Solution to temp directory<br>
     * 5. send new Solution to OutStream and return<br>
     * @param inFromClient A stream for receiving information from the client
     * @param outToClient A stream for sending information to the client
     */
    @Override
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) {
        String tempDirectoryPath = System.getProperty("java.io.tmpdir");
        ISearchingAlgorithm algorithm;
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            Maze maze = (Maze)fromClient.readObject();
            File tempDir = new File(tempDirectoryPath);
            String mazeHashPath = tempDirectoryPath.concat("//").concat(Integer.toString(maze.hashCode()));
            for (File file : Objects.requireNonNull(tempDir.listFiles())){
                if(file.getName().equals(Integer.toString(maze.hashCode()))){
                    //return the contents of the file
                    returnPrevSolution(fromClient, toClient, new File(mazeHashPath));
                    return;
                }
            }
            SearchableMaze searchableMaze = new SearchableMaze(maze);
            Configurations config = Configurations.getInstance();

            String algo = config.getConfig("server.mazeSearchingAlgorithm");
            if(Objects.equals(algo, "DFS"))
            {
                algorithm = new DepthFirstSearch();

            } else if (Objects.equals(algo, "BFS")) {
                algorithm = new BreadthFirstSearch();
            }
            else {//Best
                algorithm = new BestFirstSearch();
            }
            Solution solution = algorithm.solve(searchableMaze);
            File sol = new File(mazeHashPath);
//            System.out.println(sol.getAbsolutePath());
            FileOutputStream fi = new FileOutputStream(sol);
            ObjectOutputStream objOut = new ObjectOutputStream(fi);
            objOut.writeObject(solution);
            objOut.flush();
            objOut.close();
            toClient.writeObject(solution);
            toClient.flush();

            fromClient.close();
            toClient.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**Helper method for sending a saved solution
     * @param in A stream for receiving information from the client
     * @param out A stream for sending information to the client
     * @param solFile the File containing the solution to be sent
     */
    private void returnPrevSolution(ObjectInputStream in, ObjectOutputStream out, File solFile){

        try {
            FileInputStream fi = new FileInputStream(solFile);
            ObjectInputStream oi = new ObjectInputStream(fi);
            try { out.writeObject(oi.readObject()); }
            catch (ClassNotFoundException e) { e.printStackTrace(); return; }
            out.close();
            in.close();
            oi.close();
            fi.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
