import Controller.MainController;
import Controller.Playlist;
import Controller.Track;
import Model.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.stage.WindowEvent;

import java.util.ArrayList;

public class Main extends Application {
    public static DatabaseConnection database;
    private static MainController controller;

    private static TableView<TracksView> tracksTable = new TableView<>();
    private static TableView<Playlists> playlistsTable = new TableView<>();

    @Override
    public void start(Stage stage) {
        database = new DatabaseConnection("MusicLibrary.db");


        //scene.getStylesheets().add("Resources/DarkTheme.css");
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

        ArrayList<Playlists> playlistsArrayList = new ArrayList<>();
        PlaylistsService.selectAll(playlistsArrayList, database);
        for (Playlists c : playlistsArrayList) {
            System.out.println(c);
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}