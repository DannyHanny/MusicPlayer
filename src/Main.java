import Controller.MainController;
import Model.DatabaseConnection;
import Model.Tracks;
import Model.TracksService;
import Model.Playlists;
import Model.PlaylistsService;
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

    @Override
    public void start(Stage stage) throws Exception {
        database = new DatabaseConnection("MusicLibrary.db");

        VBox superRoot = new VBox(40);

        Scene scene = new Scene(superRoot, 1024, 768);
        //scene.getStylesheets().add("Resources/DarkTheme.css");
        stage.setTitle("The Dan Hannen Music Player");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setOnCloseRequest((WindowEvent we) -> controller.exitPrompt(we));
        stage.show();

        VBox topContainer = new VBox();
        MenuBar myMenu = new MenuBar();

        Menu fileMenu = new Menu("File");
        MenuItem fileItem1 = new MenuItem("Open...");
        MenuItem fileItem2 = new MenuItem("Add folder...");
        MenuItem fileItem3 = new MenuItem("New Playlist...");
        MenuItem fileItem4 = new MenuItem("Settings");
        MenuItem fileItem5 = new MenuItem("Exit");
        fileMenu.getItems().addAll(fileItem1, fileItem2, fileItem3, fileItem4, fileItem5);

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
        ProgressBar progressBar = new ProgressBar();
        progressBar.setPrefSize(1024, 30);
        progressBar.setProgress(0);

        slider.valueProperty().addListener(
                (observable, old_value, new_value) -> progressBar.setProgress(new_value.doubleValue() / 100)
        );

        sliderBox.getChildren().addAll(slider, progressBar);
        root.setTop(sliderBox);
        sliderBox.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(sliderBox, Pos.BOTTOM_CENTER);


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

    ObservableList<Track> tracks = FXCollections.observableArrayList(
            new Track("Losing My Edge", "LCD Soundsystem", "LCD Soundsystem"),
            new Track("Dum Surfer", "Dum Surfer", "King Krule")
    );

    ObservableList<Playlist> playlists = FXCollections.observableArrayList(
            new Playlist("Playlist 1", "12:05"),
            new Playlist("Playlist 2","2:08")
    );

    private void doSomething() {
    }

    public static void main(String[] args) {
        launch(args);
    }
}