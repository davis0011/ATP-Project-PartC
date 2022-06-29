package View;

import algorithms.mazeGenerators.Position;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.io.File;
import java.util.ArrayList;

public class MazeDisplayer extends Canvas {
    public MazeDisplayer(){
        super();
    }
    private int[][] maze;
    private Position start,goal;
    private boolean isSolved = false;
    private ArrayList<Pair<Integer,Integer>> sol;
    public void drawMaze(int[][] maze, Position start,Position goal) {
        this.maze = maze;
        this.start = start;
        this.goal = goal;
        draw();
    }
    public void drawSol(ArrayList<Pair<Integer,Integer>> sol)
    {
        this.sol = sol;
        this.isSolved = true;
        double canvasHeight = getHeight();
        double canvasWidth =  getWidth();
        int rows = maze.length;
        int cols = maze[0].length;
        double cellHeight = canvasHeight / rows;
        double cellWidth = canvasWidth / cols;
        GraphicsContext graphicsContext = this.getGraphicsContext2D();
        Image ring = new Image("ring.png");
        for (int i = 1; i <sol.size()-1; i++) {

            Pair<Integer,Integer> pair = sol.get(i);
            int row = pair.getKey();
            int col = pair.getValue();
            double x = col * cellWidth;
            double y = row * cellHeight;
            graphicsContext.drawImage(ring,x, y, cellWidth, cellHeight);

        }
    }
    public boolean move(Pair<Integer,Integer> whereto,int originrow,int origincol){
        if(whereto == null)
            return false;
        double canvasHeight = getHeight();
        double canvasWidth =  getWidth();
        int rows = maze.length;
        int cols = maze[0].length;
        double cellHeight = canvasHeight / rows;
        double cellWidth = canvasWidth / cols;
        GraphicsContext graphicsContext = this.getGraphicsContext2D();
        Image sonic = new Image("sonic.png");
        int row = whereto.getKey();
        int col = whereto.getValue();
        double x = col * cellWidth;
        double y = row * cellHeight;
        if(maze[row][col] == 1)
        {
            return false;
        }
        graphicsContext.clearRect(x,y,cellWidth,cellHeight);
        graphicsContext.drawImage(sonic,x, y, cellWidth, cellHeight);
        x = (col+origincol) * cellWidth;
        y = (row+originrow) * cellHeight;
        String path1 = "resources/collectCoin.mp3";
        Media media1 = new Media(new File(path1).toURI().toString());
        MediaPlayer mediaPlayer1 = new MediaPlayer(media1);
        mediaPlayer1.setVolume(0.5);
        if(isSolved) {
            if (this.sol.get(1).getKey() == row && this.sol.get(1).getValue() == col) {
                this.sol.remove(1);
                mediaPlayer1.play();
            }
        }
        graphicsContext.clearRect(x,y,cellWidth,cellHeight);
        if(row == goal.getRowIndex()&&col==goal.getColumnIndex())
        {
            isSolved = false;
            return true;
        }
        return false;
    }

    @Override
    public boolean isResizable() {
        return true;
    }
    private void draw() {
        if(maze != null){
            double canvasHeight = getHeight();
            double canvasWidth =  getWidth();
            int rows = maze.length;
            int cols = maze[0].length;

            double cellHeight = canvasHeight / rows;
            double cellWidth = canvasWidth / cols;

            GraphicsContext graphicsContext = this.getGraphicsContext2D();
            //clear the canvas:
            graphicsContext.clearRect(0, 0, canvasWidth, canvasHeight);
            graphicsContext.setFill(Color.BLUE);
            Image wall = new Image("wall.png");
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if(maze[i][j] == 1){
                        //if it is a wall:
                        double x = j * cellWidth;
                        double y = i * cellHeight;
                        graphicsContext.drawImage(wall,x, y, cellWidth, cellHeight);
                    }
                    if(start.getRowIndex() == i && start.getColumnIndex() == j) {
                        double x = j * cellWidth;
                        double y = i * cellHeight;
                        graphicsContext.setFill(Color.LIGHTGREEN);
                        Image image = new Image("sonic.png");
                        graphicsContext.drawImage(image,x, y, cellWidth, cellHeight);
                        graphicsContext.setFill(Color.BLUE);
                    } else if (goal.getRowIndex() == i && goal.getColumnIndex() == j) {
                        double x = j * cellWidth;
                        double y = i * cellHeight;
                        graphicsContext.setFill(Color.LIGHTGREEN);
                        Image goalrect = new Image("goal.png");
                        graphicsContext.drawImage(goalrect,x, y, cellWidth, cellHeight);
                        graphicsContext.setFill(Color.BLUE);
                    }
                }
            }
        }
    }
}
