import Model.DatabaseConnection;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    public static DatabaseConnection database;

    public static GraphicsContext gc;

    @Override
    public void start(Stage stage) throws Exception {
        database = new DatabaseConnection("Inventory.db");

        VBox root = new VBox();

        Scene scene = new Scene(root, 1024, 768);

        stage.setTitle("Hello World");
        stage.setScene(scene);
        stage.show();

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
        root.getChildren().add(myMenu);

        Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(100);
        slider.setValue(0);

        ProgressBar progressBar = new ProgressBar();
        progressBar.setPrefSize(1024, 30);
        progressBar.setProgress(0);

        slider.valueProperty().addListener(
                (observable, old_value, new_value) -> progressBar.setProgress(new_value.doubleValue() / 100)
        );

        root.getChildren().addAll(slider, progressBar);



        TableView playlistTable = new TableView<>();
        playlistTable.setPrefSize(200, 100);
        playlistTable.setItems(playlists);

        TableColumn playlistColumn = new TableColumn<>("Playlist Name");
        playlistColumn.setCellValueFactory(new PropertyValueFactory<>("playlistName"));
        playlistTable.getColumns().add(playlistColumn);

        TableColumn durationColumn = new TableColumn<>("Duration");
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("playlistDuration"));
        playlistTable.getColumns().add(durationColumn);

        root.getChildren().add(playlistTable);

        TableView tracksTable = new TableView<>();
        tracksTable.setPrefSize(200, 100);
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

        root.getChildren().add(tracksTable);


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