package View;

import Server.Configurations;
import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Position;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MyViewController implements IView, Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public TextField textField_mazeRows;
    private MyViewModel vm;
    public TextField textField_mazeColumns;
    public MazeDisplayer mazeDisplayer;
    public boolean isdone = false;

    public void setIsdone(boolean val)
    {
        this.isdone = val;
    }
    public void generateMaze(ActionEvent actionEvent) {
        if(vm == null)
            vm = new MyViewModel();
        int rows = -1;
        int cols = -1;
        try {
            rows = Integer.valueOf(textField_mazeRows.getText());
            cols = Integer.valueOf(textField_mazeColumns.getText());
            if(rows<0||cols<0){throw new Exception();}
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Sonic isn't the sharpest tool in the shed, he doesn't understand, give him whole numbers");
            alert.show();
            return;
        }


        int[][] maze = vm.genMaze(rows,cols);
        Position start,goal;
        start = vm.getStrat();
        goal = vm.getGoal();
        mazeDisplayer.drawMaze(maze,start,goal);
        Main.setIsdone(false);
    }
    public void properties(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        File res = new File(String.valueOf(MyViewController.class.getResource("resources/config.properties")));
        Configurations config = Configurations.getInstance();
        String algo = config.getConfig("server.mazeGeneratingAlgorithm");
        alert.setHeaderText(algo);//todo fix
        alert.show();
    }
    public void quit(){
        Platform.exit();
    }
    public void swithcToAbout(ActionEvent event) throws IOException {
        //URL hello = new File("C:\\Users\\david\\Desktop\\finalProject\\ATP-Project-PartC\\src\\main\\java\\View\\Help.fxml").toURI().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Help.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage1 = new Stage();
        stage1.setScene(new Scene(root));
        stage1.show();
    }
    public void about(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Some information about the technical of the maze:");
        alert.setContentText("This program was created by Noam Kent and David Shavit.\nWe are using the DFS-depth first search to create the maze" +
                "\n We use the Best First Search algorithm to solve the maze.\n");

        alert.showAndWait();
    }
    public void save(ActionEvent actionEvent) throws IOException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage stage1 = new Stage();
        File file = directoryChooser.showDialog(stage1);
        if(file == null)
            return;
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Choose name for saved maze");
        dialog.setContentText("Name:");
        Optional<String> result = dialog.showAndWait();
        String path = "";
        if (!result.isPresent()||result.get().equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Please choose valid file name");
            //alert.setContentText("Generate maze first to save it.");
            alert.showAndWait();
            return;
        }
        path = file.getPath().concat("\\").concat(result.get());
        File sol = new File(path);
        if(vm == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("No maze yet to save");
            alert.setContentText("Generate maze first to save it.");
            alert.showAndWait();
            return;
        }
            boolean succes = vm.save(sol);

    }
/*    public void newmaze(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("How to create new maze:");
        alert.setContentText("To create new maze:\n In the first textbox put rows number\n" +
                "in the second textbox put collumns number.\n" +
                "then press gen maze.");
        alert.showAndWait();
    }*/
    public void load(ActionEvent actionEvent) throws IOException {
        FileChooser chooser = new FileChooser();
        Stage stage1 = new Stage();
        File file = chooser.showOpenDialog(stage1);
        //dialog.setHeaderText("Look, a Text Input Dialog");
        int[][] maze;
        try{
            maze = vm.load(file);
        }
        catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Failed to load maze");
            alert.setContentText("Please try again");
            alert.showAndWait();
            return;
        }
        Position start,goal;
        start = vm.getStrat();
        goal = vm.getGoal();
        mazeDisplayer.drawMaze(maze,start,goal);
        Main.setIsdone(false);
    }
    public void solveMaze(ActionEvent actionEvent) {
        if(vm == null)
            vm = new MyViewModel();
        if(!vm.isThereMaze())
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("No maze yet to solve");
            alert.setContentText("Generate maze first to solve it.");

            alert.showAndWait();
            return;
        }
        mazeDisplayer.drawSol(vm.getsolution());
    }
    public boolean move(int num){
        if(vm == null)
            vm = new MyViewModel();
        if(num == 8)
            return mazeDisplayer.move(vm.moveUp(),1,0);
        else if(num == 2)
            return mazeDisplayer.move(vm.moveDown(),-1,0);
        else if(num == 4)
            return mazeDisplayer.move(vm.moveLeft(),0,1);
        else if(num == 6)
            return mazeDisplayer.move(vm.moveRight(),0,-1);
        else if (num == 9) {
            return mazeDisplayer.move(vm.moveRightUp(),1,-1);
        }
        else if (num == 7) {
            return mazeDisplayer.move(vm.moveLeftUp(),1,1);
        }
        else if (num == 1) {
            return mazeDisplayer.move(vm.moveLeftDown(),-1,1);
        }
        else if (num == 3) {
            return mazeDisplayer.move(vm.moveRightDown(),-1,-1);
        }
        return false;
    }
    InvalidationListener listener = new InvalidationListener(){
        @Override
        public void invalidated(Observable o) {
            if(vm == null)
                return;
            mazeDisplayer.drawMaze(vm.getactualmaze(),vm.getStrat(),vm.getGoal());
        }
    };
    public void showalert(String titel, String content)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(titel);
        alert.setContentText(content);
        alert.showAndWait();
        return;
    }
    public void newmaze(ActionEvent actionEvent)
    {
        int rows,cols;
        try {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setHeaderText("Please enter number of rows");
            dialog.showAndWait();
            rows = Integer.parseInt(dialog.getEditor().getText());
            dialog = new TextInputDialog();
            dialog.setHeaderText("Please enter number of columns");
            dialog.showAndWait();
            cols = Integer.parseInt(dialog.getEditor().getText());
            if(rows<0||cols<0){throw new Exception();}
        }
        catch (Exception e) {
            showalert("Sonic isn't the sharpest tool in the shed, he doesn't understand, give him whole numbers","");
            return;
        }
        if(vm == null)
            vm = new MyViewModel();
        int[][] maze = vm.genMaze(rows,cols);
        Position start,goal;
        start = vm.getStrat();
        goal = vm.getGoal();
        mazeDisplayer.drawMaze(maze,start,goal);
        Main.setIsdone(false);
    }
    public void changedSize(ActionEvent actionEvent)
    {

        mazeDisplayer.widthProperty().addListener(listener);
        mazeDisplayer.heightProperty().addListener(listener);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
