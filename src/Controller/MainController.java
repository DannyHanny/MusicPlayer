package Controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.WindowEvent;

import java.util.ArrayList;
import java.util.Optional;

public class MainController {

    private ListView<Tracks> tracksList;
    private ListView<Playlists> playlistsList;

    private DatabaseConnection database;

    public MainController(ListView<Tracks> tracksList, ListView<Playlists> playlistsList) {

        System.out.println("Initialising main controller...");

        this.tracksList = tracksList;
        this.playlistsList = playlistsList;

        database = new DatabaseConnection("");

    }
}

