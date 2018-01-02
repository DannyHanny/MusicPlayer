package Controller;

import Model.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.*;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

public class MainController {

    private final Parent view;
    //private TableView<TracksView> tracksTable;
    private TableView<Track> tracksTable;
    private TableView<Playlists> playlistsTable;

    private DatabaseConnection database;
    private ArrayList<TracksView> allTracksViews = new ArrayList<>();
    private ArrayList<Playlists> allPlaylistViews = new ArrayList<>();
    private MediaView mediaView;
    private IsMediaPlayingBinding isMediaPlayingBinding;
    private Slider slider;

    public MainController() {

        System.out.println("Initialising main controller...");

        this.view = createView();
        //this.tracksTable = tracksTable;
        //this.playlistsTable = playlistsTable;

        database = new DatabaseConnection("MusicLibrary.db");

    }

//    public void updateTables(int selectedPlaylistId, int selectedTrackId){
//        allTracksViews.clear();
//        TracksService.selectForTable(allTracksViews, database);
//
//        tracksTable.setItems(FXCollections.observableList(allTracksViews));
//
//        playlistsTable.getItems().clear();
//        PlaylistsService.selectAll(playlistsTable.getItems(), database);
//
//        if (selectedPlaylistId != 0) {
//            for (int n = 0; n < playlistsTable.getItems().size(); n++) {
//                if (playlistsTable.getItems().get(n).getPlaylistId() == selectedPlaylistId) {
//                    playlistsTable.getSelectionModel().select(n);
//                    playlistsTable.getFocusModel().focus(n);
//                    playlistsTable.scrollTo(n);
//                    break;
//                }
//            }
//        }
//
//        if (selectedTrackId != 0) {
//            for (int n = 0; n < tracksTable.getItems().size(); n++) {
//                if (tracksTable.getItems().get(n).getTrackId() == selectedTrackId) {
//                    tracksTable.getSelectionModel().select(n);
//                    tracksTable.getFocusModel().focus(n);
//                    tracksTable.scrollTo(n);
//                    break;
//                }
//            }
//        }
//
//    }

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

        this.mediaView = new MediaView();

        this.slider = new Slider();
        this.slider.setMin(0);
        this.slider.setMax(1024);
        this.slider.setValue(0);
        this.slider.disableProperty().bind(this.mediaView.mediaPlayerProperty().isNull());

        VBox sliderBox = new VBox();

        HBox playControls = new HBox();

        ImageView play = new ImageView("Resources/play.png");
        play.setFitHeight(30);
        play.setFitWidth(30);

        Button playBtn = new Button();
        playBtn.setGraphic(play);
        playBtn.setStyle("-fx-background-color: transparent; -fx-padding: 5, 5, 5, 5;");
        this.isMediaPlayingBinding = new IsMediaPlayingBinding(this.mediaView);
        playBtn.setOnAction(event -> {
            this.mediaView.getMediaPlayer().play();
        });
        playBtn.disableProperty().bind(this.mediaView.mediaPlayerProperty().isNull().or(isMediaPlayingBinding));

        ImageView pause = new ImageView("Resources/pause.png");
        pause.setFitHeight(33);
        pause.setFitWidth(33);

        Button pauseBtn = new Button();
        pauseBtn.setGraphic(pause);
        pauseBtn.setStyle("-fx-background-color: transparent; -fx-padding: 5, 5, 5, 5;");
        pauseBtn.disableProperty().bind(this.mediaView.mediaPlayerProperty().isNull().or(isMediaPlayingBinding.not()));
        pauseBtn.setOnAction(event -> this.mediaView.getMediaPlayer().pause());

        playControls.getChildren().addAll(playBtn, pauseBtn);
        ProgressBar progressBar = new ProgressBar();
        progressBar.setPrefSize(1024, 30);
        progressBar.setProgress(0);

        slider.valueProperty().addListener(
                observable -> {
                    if (slider.isValueChanging()) {
                        MediaPlayer mediaPlayer = this.mediaView.getMediaPlayer();
                        Duration totalDuration = mediaPlayer.getTotalDuration();
                        if (!totalDuration.isIndefinite() && !totalDuration.isUnknown()) {
                            double progress = slider.getValue() / 1024;
                            mediaPlayer.seek(totalDuration.multiply(progress));
                            progressBar.setProgress(progress);
                        }
                    }
                }
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
        this.tracksTable = new TableView<>();
        tracksTable.setPrefSize(733, 1000);
        tracksTable.setItems(tracks);
        tracksTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue.getMediaPlayer().isPresent()) {
                    // optimisation where we just added the file to the list of tracks so we already have the Media object
                    MediaPlayer mp = newValue.getMediaPlayer().get();
                    this.mediaView.setMediaPlayer(mp);
                    mp.setOnPlaying(() -> this.isMediaPlayingBinding.invalidate());
                    mp.setOnPaused(() -> this.isMediaPlayingBinding.invalidate());

                    mp.currentTimeProperty().addListener(o2 -> {
                        Platform.runLater(() -> {
                            Duration currentTime = mp.getCurrentTime();
                            Duration duration = mp.getTotalDuration();
                            if (!slider.isDisabled()
                                    && duration.greaterThan(Duration.ZERO)
                                    && !slider.isValueChanging()) {
                                int progress = (int) (1024.0 * currentTime.toSeconds() / duration.toSeconds());
                                if (progress > 0) {
                                    slider.setValue(progress);
                                }
                            }

                        });
                    });
                } else {

                }

            }
        });

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
        String absolutePath = file.getAbsolutePath();
        try {
            final Media media = new Media(file.toURI().toString());
            final MediaPlayer mp = new MediaPlayer(media);
            mp.setOnReady(() -> {
                System.out.println(media.getMetadata());
                System.out.println(media.getTracks());

                if (!IsSingleAudioTrack(media)) {
                    displayError("Can only add single audio tracks");
                    return;
                }

                Result<Track> result = addToDb(media);
                if (result.fail()) {
                    displayError("Unexpected error adding track");
                    return;
                }

                Track t = result.result();
                t.setMediaPlayer(mp);
                this.tracksTable.getItems().add(t);
                this.tracksTable.getSelectionModel().select(this.tracksTable.getItems().size() - 1);

            });
        } catch (MediaException e) {
            if (e.getType() == MediaException.Type.MEDIA_UNSUPPORTED) {
                displayError("Not a playable file");
            }
        }
    }

    private Result<Track> addToDb(Media media) {
        ObservableMap<String, Object> metadata = media.getMetadata();
        return new Result(new Track((String) metadata.get("title"), (String) metadata.get("album"), (String) metadata.get("artist")));
    }

    private boolean IsSingleAudioTrack(Media media) {
        ObservableList<javafx.scene.media.Track> tracks = media.getTracks();
        return tracks.size() == 1 && tracks.get(0) instanceof AudioTrack;
    }

    private boolean fileExistsInDb(String absolutePath) {
        return false;
    }


    public Parent view() {
        return this.view;
    }

}

