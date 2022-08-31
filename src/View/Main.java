package View;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Main extends Application{
    static final boolean[] isdone = {false};
    @Override
    public void start(Stage stage) throws IOException, InterruptedException {

        String path = "resources/sonicmusic.mp3";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(0.3);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
            }
        });
        mediaPlayer.play();
        //URL hello = new File("C:\\Users\\david\\Desktop\\finalProject\\ATP-Project-PartC\\src\\main\\java\\View\\MyView.fxml").toURI().toURL();
        FXMLLoader root = new FXMLLoader(getClass().getResource("MyView.fxml"));
        Scene scene = new Scene(root.load());
        stage.setTitle("Sonic Maze");
        //stage.initStyle(StageStyle.TRANSPARENT);
        MyViewController controller = root.getController();
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(!isdone[0]) {
                    switch (keyEvent.getCode()) {
                        case NUMPAD8: {
                            isdone[0] = controller.move(8);
                            break;
                        }
                        case NUMPAD4: {
                            isdone[0] = controller.move(4);
                            break;
                        }
                        case NUMPAD6: {
                            isdone[0] = controller.move(6);
                            break;
                        }
                        case NUMPAD2: {
                            isdone[0] = controller.move(2);
                            break;
                        }
                        case NUMPAD9: {
                            isdone[0] = controller.move(9);
                            break;
                        }
                        case NUMPAD7: {
                            isdone[0] = controller.move(7);
                            break;
                        }
                        case NUMPAD1: {
                            isdone[0] = controller.move(1);
                            break;
                        }
                        case NUMPAD3: {
                            isdone[0] = controller.move(3);
                            break;
                        }
                    }
                if(isdone[0])
                {
                    mediaPlayer.stop();
                    String path1 = "resources/victory.mp3";
                    Media media1 = new Media(new File(path1).toURI().toString());
                    MediaPlayer mediaPlayer1 = new MediaPlayer(media1);
                    mediaPlayer1.setVolume(0.5);
                    mediaPlayer1.play();
                    mediaPlayer.play();
                    mediaPlayer.setAutoPlay(true);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Sonic has found EggMan and won him");
                    alert.setContentText("Sonic thank you for helping him and offering you one of his coins:");
                    javafx.scene.image.Image ring = new Image("ring.png");
                    ImageView imageView = new ImageView(ring);
                    alert.setGraphic(imageView);
                    alert.showAndWait();
                }
                }
            }
        });

        stage.setScene(scene);
        MazeDisplayer disp = controller.mazeDisplayer;
//        disp.widthProperty().bind(
//                stage.widthProperty());
//        disp.heightProperty().bind(
//                stage.heightProperty());
        //disp.resize(600,700);
        stage.show();
    }
    public static void setIsdone(boolean val)
    {
        isdone[0] = val;
    }

    public static void main(String[] args) {
        launch();
    }
}
