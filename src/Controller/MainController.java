package Controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

public class MainController {

    private final Parent view;
    private TableView<TracksView> tracksTable;
    private TableView<Playlists> playlistsTable;

    private DatabaseConnection database;
    private ArrayList<TracksView> allTracksViews = new ArrayList<>();
    private ArrayList<Playlists> allPlaylistViews = new ArrayList<>();
    private MediaView mediaView;
    private IsMediaPlayingBinding isMediaPlayingBinding;

    public MainController() {

        System.out.println("Initialising main controller...");

        this.view = createView();
        //this.tracksTable = tracksTable;
        //this.playlistsTable = playlistsTable;

        database = new DatabaseConnection("MusicLibrary.db");

        //updateTables(0, 0);
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

    private Parent createView() {
        VBox superRoot = new VBox(0);

        VBox topContainer = new VBox();
        MenuBar myMenu = new MenuBar();

        Menu fileMenu = new Menu("File");
        MenuItem openItem = new MenuItem("Open...");
        final FileChooser fileChooser = new FileChooser();
        openItem.setOnAction(event -> {
            MenuItem m = (MenuItem) event.getSource();
            while (m.getParentPopup() == null) {
                m = m.getParentMenu();
            }

            Window w = m.getParentPopup().getOwnerWindow();
            File file = fileChooser.showOpenDialog(w);
            if (file != null) {
                this.openFile(file);
            }
        });
        MenuItem fileItem2 = new MenuItem("Add folder...");
        MenuItem fileItem3 = new MenuItem("New Playlist...");
        MenuItem fileItem4 = new MenuItem("Settings");
        MenuItem fileItem5 = new MenuItem("Exit");
        fileMenu.getItems().addAll(openItem, fileItem2, fileItem3, fileItem4, fileItem5);

        Menu editMenu = new Menu("Edit");
        MenuItem editItem1 = new MenuItem("Undo");
        MenuItem editItem2 = new MenuItem("Redo");
        MenuItem editItem3 = new MenuItem("Select all");
        editMenu.getItems().addAll(editItem1, editItem2, editItem3);

        Menu shapesMenu = new Menu("View");
        MenuItem shapesItem1 = new MenuItem("Triangle");
        MenuItem shapesItem2 = new MenuItem("Square");
        MenuItem shapesItem3 = new MenuItem("Circle");
        shapesMenu.getItems().addAll(shapesItem1, shapesItem2, shapesItem3);

        Menu helpMenu = new Menu("Help");
        MenuItem helpItem1 = new MenuItem("About");
        helpMenu.getItems().addAll(helpItem1);

        myMenu.getMenus().addAll(fileMenu, editMenu, shapesMenu, helpMenu);
        topContainer.getChildren().add(myMenu);
        superRoot.getChildren().add(topContainer);

        BorderPane root = new BorderPane();
        superRoot.getChildren().add(root);

        Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(100);
        slider.setValue(0);

        VBox sliderBox = new VBox();

        ImageView play = new ImageView("Resources/play.png");
        play.setFitHeight(30);
        play.setFitWidth(30);

        this.mediaView = new MediaView();
        HBox playControls = new HBox();
        Button playBtn = new Button();
        playBtn.setGraphic(play);
        playBtn.setStyle("-fx-background-color: transparent; -fx-padding: 5, 5, 5, 5;");
        this.isMediaPlayingBinding = new IsMediaPlayingBinding(this.mediaView);
        playBtn.setOnAction(event -> {
            this.mediaView.getMediaPlayer().play();
        });
        playBtn.disableProperty().bind(this.mediaView.mediaPlayerProperty().isNull().or(isMediaPlayingBinding));

        Button pauseBtn = new Button("Pause");
        pauseBtn.disableProperty().bind(this.mediaView.mediaPlayerProperty().isNull().or(isMediaPlayingBinding.not()));
        pauseBtn.setOnAction(event -> this.mediaView.getMediaPlayer().pause());

        playControls.getChildren().addAll(playBtn, pauseBtn);
        ProgressBar progressBar = new ProgressBar();
        progressBar.setPrefSize(1024, 30);
        progressBar.setProgress(0);

        slider.valueProperty().addListener(
                (observable, old_value, new_value) -> progressBar.setProgress(new_value.doubleValue() / 100)
        );

        this.mediaView.mediaPlayerProperty().addListener((observable, oldValue, newValue) -> {
        });
        sliderBox.getChildren().addAll(this.mediaView, playControls, slider, progressBar);
        root.setTop(sliderBox);
        sliderBox.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(sliderBox, Pos.BOTTOM_CENTER);

        ObservableList<Playlist> playlists = FXCollections.observableArrayList(
                new Playlist("Playlist 1", "12:05"),
                new Playlist("Playlist 2", "2:08")
        );
        VBox leftPane = new VBox();
        TableView playlistTable = new TableView<>();
        playlistTable.setPrefSize(300, 500);
        playlistTable.setItems(playlists);

        TableColumn playlistColumn = new TableColumn<>("Playlist Name");
        playlistColumn.setCellValueFactory(new PropertyValueFactory<>("playlistName"));
        playlistTable.getColumns().add(playlistColumn);

        TableColumn durationColumn = new TableColumn<>("Duration");
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("playlistDuration"));
        playlistTable.getColumns().add(durationColumn);

        playlistColumn.setPrefWidth(223);
        durationColumn.setPrefWidth(75);

        root.setLeft(leftPane);
        leftPane.getChildren().add(playlistTable);
        leftPane.setAlignment(Pos.TOP_CENTER);
        BorderPane.setAlignment(leftPane, Pos.CENTER_LEFT);


        ObservableList<Track> tracks = FXCollections.observableArrayList(
                new Track("Losing My Edge", "LCD Soundsystem", "LCD Soundsystem"),
                new Track("Dum Surfer", "Dum Surfer", "King Krule")
        );
        VBox centrePane = new VBox();
        TableView tracksTable = new TableView<>();
        tracksTable.setPrefSize(733, 1000);
        tracksTable.setItems(tracks);

        TableColumn trackColumn = new TableColumn<>("Track Title");
        trackColumn.setCellValueFactory(new PropertyValueFactory<>("trackTitle"));
        tracksTable.getColumns().add(trackColumn);

        TableColumn albumColumn = new TableColumn<>("Album");
        albumColumn.setCellValueFactory(new PropertyValueFactory<>("album"));
        tracksTable.getColumns().add(albumColumn);

        TableColumn artistColumn = new TableColumn<>("Artist");
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));
        tracksTable.getColumns().add(artistColumn);

        trackColumn.setPrefWidth(244);
        albumColumn.setPrefWidth(244);
        artistColumn.setPrefWidth(244);

        root.setRight(centrePane);
        centrePane.getChildren().add(tracksTable);
        centrePane.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(centrePane, Pos.CENTER);

        ImageView imageView = new ImageView("Resources/LCD.jpg");
        imageView.setFitHeight(300);
        imageView.setFitWidth(300);
        imageView.setPreserveRatio(true);

        leftPane.getChildren().add(imageView);

        return superRoot;
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

    public void openFile(File file) {
        try {
            final Media media = new Media(file.toURI().toString());
            MediaPlayer mp = new MediaPlayer(media);
            this.mediaView.setMediaPlayer(mp);
            mp.setOnPlaying(() -> this.isMediaPlayingBinding.invalidate());
            mp.setOnPaused(() -> this.isMediaPlayingBinding.invalidate());
        } catch (MediaException e) {
            if (e.getType() == MediaException.Type.MEDIA_UNSUPPORTED) {
                displayError("Not a playable file");
            }
        }
    }

    public Parent view() {
        return this.view;
    }

}

