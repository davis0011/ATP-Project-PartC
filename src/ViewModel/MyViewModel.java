package ViewModel;

import Model.MyModel;
import algorithms.mazeGenerators.Position;
import javafx.scene.control.Alert;
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MyViewModel {

    MyModel model;
    public int[][] genMaze(int rows,int cols)
    {
        if(model == null)
            model = new MyModel();
        return model.genMaze(rows,cols);
    }
    public Position getStrat(){
        return model.getStart();
    }
    public Position getGoal(){
        return model.getGoal();
    }
    public ArrayList<Pair<Integer,Integer>> getsolution(){
        return model.solvemaze();
    }
    public boolean isThereMaze(){
        if(model == null)
            model = new MyModel();
        return model.isThereMaze();
    }
    public Pair<Integer,Integer> moveUp(){
        if(model == null)
            return null;
        return model.moveUp();
    }

    public Pair<Integer, Integer> moveDown() {
        if(model == null)
            return null;
        return model.moveDown();
    }

    public Pair<Integer, Integer> moveLeft() {
        if(model == null)
            return null;
        return model.moveLeft();
    }
    public Pair<Integer, Integer> moveRight() {
        if(model == null)
            return null;
        return model.moveRight();
    }
    public Pair<Integer, Integer> moveRightUp() {
        if(model == null)
            return null;
        return model.moveRightUp();
    }
    public Pair<Integer, Integer> moveLeftUp() {
        if(model == null)
            return null;
        return model.moveLeftUp();
    }
    public Pair<Integer, Integer> moveLeftDown() {
        if(model == null)
            return null;
        return model.moveLeftDown();
    }
    public Pair<Integer, Integer> moveRightDown() {
        if(model == null)
            return null;
        return model.moveRightDown();
    }
    public boolean save(File file) throws IOException {
        if(model == null)
            return false;
        return model.savefile(file);

    }

    public int[][] load(File file) throws IOException {
        if(model == null)
            model = new MyModel();
        return model.loadFile(file);
    }
    public int[][] getactualmaze(){
        if(model == null)
            return null;
        return model.getActualMaze();
    }

    public void writeError(String s,Exception e) {
        if(model == null)
            model = new MyModel();
        model.writeError(s,e);
    }
}
