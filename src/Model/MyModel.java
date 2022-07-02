package Model;

import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

public class MyModel implements IModel{
    Maze maze;
    boolean thereIsMaze = false;
    int[][] actualMaze;
    Position currstate;
    SearchableMaze search;
    public IMazeGenerator generator;
    public Position currpos;
    public int i=1;
    private boolean isSolved = false;
    private static final Logger LOG = LogManager.getLogger();

    @Override
    public int[][] genMaze(int rows, int cols) {
        if(generator == null)
            generator = new MyMazeGenerator();
        thereIsMaze = true;
        this.maze = generator.generate(rows, cols);
        this.actualMaze = maze.getFullMaze();
        this.currstate = maze.getStartPosition();
        LOG.info("Maze number: "+i+" has been generated");
        isSolved = false;
        i++;
        return actualMaze;
    }
    public Position getStart()
    {
        return maze.getStartPosition();
    }
    public Position getGoal()
    {
        return maze.getGoalPosition();
    }
    public ArrayList<Pair<Integer,Integer>> solvemaze(){
        search = new SearchableMaze(maze);
        ISearchingAlgorithm best = new BestFirstSearch();
        Solution solution = best.solve(search);
        ArrayList<AState> solutionPath = solution.getSolutionPath();
        ArrayList<Pair<Integer,Integer>> tupArr = new ArrayList<>();
        for(int i = 0; i < solutionPath.size(); ++i) {
            int row,col;
            MazeState state = (MazeState) solutionPath.get(i);
            row = state.getPosition().getRowIndex();
            col = state.getPosition().getColumnIndex();
            tupArr.add(new Pair<>(row,col));
        }
        if(!isSolved) {
            isSolved = true;
            LOG.info("Maze solved! we used the best first search algorithm");
        }
        return tupArr;
    }

    public boolean isThereMaze() {
        if(this.maze == null){
            return false;
        }
        return true;
    }
    private void hitwall(){
        String path = "resources/loseRing.mp3";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(0.6);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.play();
    }
    public Pair<Integer,Integer> moveUp(){
        if(currstate.getRowIndex()!=0)
            if(actualMaze[currstate.getRowIndex()-1][currstate.getColumnIndex()]==1) {
                hitwall();
                return null;
            }
            else
                currstate.setRow(currstate.getRowIndex()-1);
        else{
            return null;
        }
        Pair<Integer,Integer> position = new Pair<>(currstate.getRowIndex(),currstate.getColumnIndex());
        return position;
    }
    public Pair<Integer,Integer> moveLeft(){
        if(currstate.getColumnIndex()!=0)
            if(actualMaze[currstate.getRowIndex()][currstate.getColumnIndex()-1]==1) {
                hitwall();
                return null;
            }
            else
                currstate.setCol(currstate.getColumnIndex()-1);
        else{
            return null;
        }
        Pair<Integer,Integer> position = new Pair<>(currstate.getRowIndex(),currstate.getColumnIndex());
        return position;
    }
    public Pair<Integer,Integer> moveDown(){
        if(currstate.getRowIndex()<actualMaze.length-1)
            if(actualMaze[currstate.getRowIndex()+1][currstate.getColumnIndex()]==1) {
                hitwall();
                return null;
            }
            else
                currstate.setRow(currstate.getRowIndex()+1);
        else{
            return null;
        }
        Pair<Integer,Integer> position = new Pair<>(currstate.getRowIndex(),currstate.getColumnIndex());
        return position;
    }
    public Pair<Integer,Integer> moveRight(){
        if(currstate.getColumnIndex()<actualMaze[0].length-1)
            if(actualMaze[currstate.getRowIndex()][currstate.getColumnIndex()+1]==1) {
                hitwall();
                return null;
            }
            else
                currstate.setCol(currstate.getColumnIndex()+1);
        else{
            return null;
        }
        Pair<Integer,Integer> position = new Pair<>(currstate.getRowIndex(),currstate.getColumnIndex());
        return position;
    }
    public Pair<Integer,Integer> moveRightUp(){
        if(currstate.getColumnIndex()<actualMaze[0].length-1&&currstate.getRowIndex()!=0) {
            if (actualMaze[currstate.getRowIndex() - 1][currstate.getColumnIndex() + 1] == 1) {
                hitwall();
                return null;
            }
            else if (actualMaze[currstate.getRowIndex()][currstate.getColumnIndex() + 1] == 0 ||
                    actualMaze[currstate.getRowIndex() - 1][currstate.getColumnIndex()] == 0) {
                currstate.setRow(currstate.getRowIndex() - 1);
                currstate.setCol(currstate.getColumnIndex() + 1);
            } else {
                hitwall();
                return null;
            }
        }
        else
            return null;
        Pair<Integer,Integer> position = new Pair<>(currstate.getRowIndex(),currstate.getColumnIndex());
        return position;
    }
    public Pair<Integer,Integer> moveLeftUp(){
        if(currstate.getColumnIndex()!=0&&currstate.getRowIndex()!=0) {
            if (actualMaze[currstate.getRowIndex() - 1][currstate.getColumnIndex() - 1] == 1) {
                hitwall();
                return null;
            }
            else if (actualMaze[currstate.getRowIndex()][currstate.getColumnIndex() - 1] == 0 ||
                    actualMaze[currstate.getRowIndex() - 1][currstate.getColumnIndex()] == 0) {
                currstate.setRow(currstate.getRowIndex() - 1);
                currstate.setCol(currstate.getColumnIndex() - 1);
            } else {
                hitwall();
                return null;
            }
        }
        else
            return null;
        Pair<Integer,Integer> position = new Pair<>(currstate.getRowIndex(),currstate.getColumnIndex());
        return position;
    }
    public Pair<Integer,Integer> moveLeftDown(){
        if(currstate.getColumnIndex()!=0&&currstate.getRowIndex()<actualMaze.length-1) {
            if (actualMaze[currstate.getRowIndex() + 1][currstate.getColumnIndex() - 1] == 1)
            {
                hitwall();
                return null;
            }
            else if (actualMaze[currstate.getRowIndex()][currstate.getColumnIndex() - 1] == 0 ||
                    actualMaze[currstate.getRowIndex() + 1][currstate.getColumnIndex()] == 0) {
                currstate.setRow(currstate.getRowIndex() + 1);
                currstate.setCol(currstate.getColumnIndex() - 1);
            } else {
                hitwall();
                return null;
            }
        }
        else
            return null;
        Pair<Integer,Integer> position = new Pair<>(currstate.getRowIndex(),currstate.getColumnIndex());
        return position;
    }
    public Pair<Integer,Integer> moveRightDown(){
        if(currstate.getColumnIndex()<actualMaze[0].length-1&&currstate.getRowIndex()<actualMaze.length-1) {
            if (actualMaze[currstate.getRowIndex() + 1][currstate.getColumnIndex() + 1] == 1)
            {
                hitwall();
                return null;
            }
            else if (actualMaze[currstate.getRowIndex()][currstate.getColumnIndex() + 1] == 0 ||
                    actualMaze[currstate.getRowIndex() + 1][currstate.getColumnIndex()] == 0) {
                currstate.setRow(currstate.getRowIndex() + 1);
                currstate.setCol(currstate.getColumnIndex() + 1);
            } else {
                hitwall();
                return null;
            }
        }
        else
            return null;
        Pair<Integer,Integer> position = new Pair<>(currstate.getRowIndex(),currstate.getColumnIndex());
        return position;
    }
    public boolean savefile(File file) {
        if(!thereIsMaze)
            return false;
        try {
            FileOutputStream fi = new FileOutputStream(file);
            ObjectOutputStream obj = new ObjectOutputStream(fi);
            fi.write(maze.toByteArray());
        } catch (IOException e) {
            LOG.error("Failed to save the maze...",e);
            return false;
        }
        return true;
    }


    public int[][] loadFile(File file) throws IOException {
        if(generator == null)
            generator = new MyMazeGenerator();
        FileInputStream fi = new FileInputStream(file);
        ObjectInputStream obj = new ObjectInputStream(fi);
        thereIsMaze = true;
        this.maze = new Maze(fi.readAllBytes());
        this.actualMaze = maze.getFullMaze();
        this.currstate = maze.getStartPosition();
        isSolved = false;
        LOG.info("The maze: "+ file.getName()+" has been loaded");
        return actualMaze;
    }
    public int[][] getActualMaze(){
        return this.actualMaze;
    }

    public void writeError(String s,Exception e) {
        if(e==null)
            LOG.error(s);
        else{
            LOG.error(s,e);
        }
    }
}
