package Controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.stage.WindowEvent;

import java.util.ArrayList;
import java.util.Optional;

public class MainController {

    private TableView<TracksView> tracksTable;
    private TableView<Playlists> playlistsTable;

    private DatabaseConnection database;

    public MainController(ListView<Tracks> tracksList, ListView<Playlists> playlistsList) {

        System.out.println("Initialising main controller...");

        this.tracksTable = tracksTable;
        this.playlistsTable = playlistsTable;

        database = new DatabaseConnection("MusicLibrary.db");
    }

    public void updateTracks(){

    }

    public void exitPrompt(WindowEvent we) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Music Player");
        alert.setHeaderText("Are you sure you want to exit?");

        Optional result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            database.disconnect();
            System.exit(0);
        } else {
            we.consume();
        }

    }

    private void displayError(String errorMessage) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);
        alert.showAndWait();

    }

}

