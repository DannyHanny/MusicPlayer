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
    private ArrayList<TracksView> allTracksViews = new ArrayList<>();
    private ArrayList<Playlists> allPlaylistViews = new ArrayList<>();

    public MainController(TableView<TracksView> tracksTable, TableView<Playlists> playlistsTable) {

        System.out.println("Initialising main controller...");

        this.tracksTable = tracksTable;
        this.playlistsTable = playlistsTable;

        database = new DatabaseConnection("MusicLibrary.db");

        updateTables(0, 0);
    }

    public void updateTables(int selectedPlaylistId, int selectedTrackId){
        allTracksViews.clear();
        TracksService.selectForTable(allTracksViews, database);

        tracksTable.setItems(FXCollections.observableList(allTracksViews));

        playlistsTable.getItems().clear();
        PlaylistsService.selectAll(playlistsTable.getItems(), database);

        if (selectedPlaylistId != 0) {
            for (int n = 0; n < playlistsTable.getItems().size(); n++) {
                if (playlistsTable.getItems().get(n).getPlaylistId() == selectedPlaylistId) {
                    playlistsTable.getSelectionModel().select(n);
                    playlistsTable.getFocusModel().focus(n);
                    playlistsTable.scrollTo(n);
                    break;
                }
            }
        }

        if (selectedTrackId != 0) {
            for (int n = 0; n < tracksTable.getItems().size(); n++) {
                if (tracksTable.getItems().get(n).getTrackId() == selectedTrackId) {
                    tracksTable.getSelectionModel().select(n);
                    tracksTable.getFocusModel().focus(n);
                    tracksTable.scrollTo(n);
                    break;
                }
            }
        }

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

