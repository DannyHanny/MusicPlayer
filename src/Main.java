import Controller.MainController;
import Model.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    public static DatabaseConnection database;
    private static MainController controller;

    @Override
    public void start(Stage stage) {
        database = new DatabaseConnection("MusicLibrary.db");

        controller = new MainController();

        Scene scene = new Scene(controller.view(), 1024, 768);
        stage.setTitle("The Dan Hannen Music Player");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setOnCloseRequest((WindowEvent we) -> controller.exitPrompt(we));
        stage.show();

        ArrayList<Tracks> testList = new ArrayList<>();
        TracksService.selectAll(testList, database);
        for (Tracks c : testList) {
            System.out.println(c);
        }

        List<Playlists> playlistsArrayList = PlaylistsService.selectAll(database);
        for (Playlists c : playlistsArrayList) {
            System.out.println(c);
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}