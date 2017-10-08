import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        VBox root = new VBox();

        Scene scene = new Scene(root, 1024, 768);

        stage.setTitle("Hello World");
        stage.setScene(scene);
        stage.show();

        MenuBar myMenu = new MenuBar();

        Menu numbersMenu = new Menu("File");
        MenuItem numbersItem1 = new MenuItem("One");
        MenuItem numbersItem2 = new MenuItem("Two");
        MenuItem numbersItem3 = new MenuItem("Three");
        numbersMenu.getItems().addAll(numbersItem1, numbersItem2, numbersItem3);

        Menu coloursMenu = new Menu("Edit");
        MenuItem coloursItem1 = new MenuItem("Red");
        MenuItem coloursItem2 = new MenuItem("Green");
        MenuItem coloursItem3 = new MenuItem("Blue");
        coloursMenu.getItems().addAll(coloursItem1, coloursItem2, coloursItem3);

        Menu shapesMenu = new Menu("View");
        MenuItem shapesItem1 = new MenuItem("Triangle");
        MenuItem shapesItem2 = new MenuItem("Square");
        MenuItem shapesItem3 = new MenuItem("Circle");
        shapesMenu.getItems().addAll(shapesItem1, shapesItem2, shapesItem3);

        myMenu.getMenus().addAll(numbersMenu, coloursMenu, shapesMenu);
        root.getChildren().add(myMenu);

        Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(100);
        slider.setValue(0);

        ProgressBar progressBar = new ProgressBar();
        progressBar.setPrefSize(1000, 30);
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