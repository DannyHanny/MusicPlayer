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
    private TableView<TracksView> tracksTable;
    private TableView<Playlists> playlistsTable;

    private DatabaseConnection database;
    private ArrayList<TracksView> allTracksViews = new ArrayList<>();
    private ArrayList<Playlists> allPlaylistViews = new ArrayList<>();
    private MediaView mediaView;
    private IsMediaPlayingBinding isMediaPlayingBinding;
    private Slider slider;

    public MainController() {

        System.out.println("Initialising main controller...");

        database = new DatabaseConnection("MusicLibrary.db");

        this.view = createView();
    }

    private Parent createView() {
        VBox superRoot = new VBox(0);

        VBox topContainer = new VBox();
        MenuBar myMenu = new MenuBar();

        Menu fileMenu = new Menu("File");
        MenuItem newPlaylistItem = new MenuItem("New Playlist...");
        newPlaylistItem.setOnAction(event -> {
            TextInputDialog inputDialog = new TextInputDialog();
            inputDialog.setTitle("Playlist");
            inputDialog.setHeaderText("Enter playlist name");
            inputDialog.setContentText("Name");
            Optional<String> name = inputDialog.showAndWait();
            if (name.isPresent() && !name.get().isEmpty()) {
                Result<Playlists> result = PlaylistsService.CreatePlaylist(name.get(), database);
                if (result.success())
                {
                    Playlists createdPlaylist = result.payload();
                    this.playlistsTable.getItems().add(createdPlaylist);
                    this.playlistsTable.getSelectionModel().select(this.playlistsTable.getItems().size() - 1);

                } else {
                    displayError(result.error());
                }
            }
        });
        MenuItem addTrackItem = new MenuItem("Add Track...");
        final FileChooser fileChooser = new FileChooser();
        addTrackItem.setOnAction(event -> {
            MenuItem m = (MenuItem) event.getSource();
            while (m.getParentPopup() == null) {
                m = m.getParentMenu();
            }

            Window w = m.getParentPopup().getOwnerWindow();
            File file = fileChooser.showOpenDialog(w);
          if (file != null) {
              this.addTrack(file);
          }
        });
        MenuItem fileItem2 = new MenuItem("Add folder...");
        fileItem2.setOnAction(event -> {featureNotYetImplemented();});
        MenuItem fileItem4 = new MenuItem("Settings");
        fileItem4.setOnAction(event -> {featureNotYetImplemented();});
        MenuItem fileItem5 = new MenuItem("Exit");
        fileItem5.setOnAction(event -> {featureNotYetImplemented();});
        fileMenu.getItems().addAll(newPlaylistItem, addTrackItem, fileItem2, fileItem4, fileItem5);

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

        //this.mediaView.mediaPlayerProperty().addListener((observable, oldValue, newValue) -> { });
        sliderBox.getChildren().addAll(this.mediaView, playControls, slider, progressBar);
        root.setTop(sliderBox);
        sliderBox.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(sliderBox, Pos.BOTTOM_CENTER);

        VBox leftPane = new VBox();
        this.playlistsTable = new TableView<>();
        this.playlistsTable.setPrefSize(300, 500);
        addTrackItem.disableProperty().bind(this.playlistsTable.getSelectionModel().selectedItemProperty().isNull());

        TableColumn playlistColumn = new TableColumn<>("Playlist Name");
        playlistColumn.setCellValueFactory(new PropertyValueFactory<>("playlistName"));
        this.playlistsTable.getColumns().add(playlistColumn);
        this.playlistsTable.setItems(FXCollections.observableList(PlaylistsService.selectAll(database)));
        this.playlistsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                this.tracksTable.setItems(FXCollections.observableList(TracksService.selectForTable(newValue, database)));
            }
        });

        TableColumn durationColumn = new TableColumn<>("Duration");
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("playlistDuration"));
        this.playlistsTable.getColumns().add(durationColumn);

        playlistColumn.setPrefWidth(223);
        durationColumn.setPrefWidth(75);

        root.setLeft(leftPane);
        leftPane.getChildren().add(this.playlistsTable);
        leftPane.setAlignment(Pos.TOP_CENTER);
        BorderPane.setAlignment(leftPane, Pos.CENTER_LEFT);


        VBox centrePane = new VBox();
        this.tracksTable = new TableView<>();
        tracksTable.setPrefSize(733, 1000);
        tracksTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
              if (newValue != null) {
                  if (newValue.getMediaPlayer().isPresent()) {

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

              }      });

        TableColumn trackColumn = new TableColumn<>("Track Title");
        trackColumn.setCellValueFactory(new PropertyValueFactory<>("trackName"));
        tracksTable.getColumns().add(trackColumn);

        TableColumn albumColumn = new TableColumn<>("Album");
        albumColumn.setCellValueFactory(new PropertyValueFactory<>("albumName"));
        tracksTable.getColumns().add(albumColumn);

        TableColumn artistColumn = new TableColumn<>("Artist");
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artistName"));
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

    private void featureNotYetImplemented(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText(null);
        alert.setContentText("Feature not yet implemented.");
        alert.showAndWait();
    }

  public void addTrack(File file) {
      String absolutePath = file.getAbsolutePath();
      if (fileExistsInDb(absolutePath)){
          displayError("File already saved");
      }
      else{
          try {
              final Media media = new Media(file.toURI().toString());
              final MediaPlayer mp = new MediaPlayer(media);
              mp.setOnReady(() -> {

                  if (!IsSingleAudioTrack(media)) {
                      displayError("Can only add single audio tracks");
                      return;
                  }

                  ObservableMap<String, Object> metadata = media.getMetadata();
                  String title = (String) metadata.get("title");
                  String album = (String) metadata.get("album");
                  String artist = (String) metadata.get("artist");
                  Result<Tracks> result = TracksService.CreateTrack(absolutePath, title, album, artist, database);
                  if (result.fail()) {
                      displayError("Unexpected error adding track");
                      return;
                  }

                  Tracks track = result.payload();
                  PlaylistsService.AddTrackToPlaylist(playlistsTable.getSelectionModel().getSelectedItem(), track, database);
                  if (result.fail()) {
                      displayError("Unexpected error adding track");
                      return;
                  }

                  TracksView trackView = new TracksView(track.getTrackId(), title, album, artist, "unknown");
                  trackView.setMediaPlayer(mp);
                  this.tracksTable.getItems().add(trackView);
                  this.tracksTable.getSelectionModel().select(this.tracksTable.getItems().size() - 1);

              });
      } catch (MediaException e) {
              if (e.getType() == MediaException.Type.MEDIA_UNSUPPORTED) {
                  displayError("Not a playable file");
              }
          }
      }
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

