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
        slider.setValue(50);

        ProgressBar progressBar = new ProgressBar();
        progressBar.setPrefSize(400, 30);
        progressBar.setProgress(0.5);

        slider.valueProperty().addListener(
                (observable, old_value, new_value) -> progressBar.setProgress(new_value.doubleValue() / 100)
        );

        root.getChildren().addAll(slider, progressBar);

        TableView table = new TableView<>();
        table.setPrefSize(200, 100);
        table.setItems(tracks);

        TableColumn firstNameColumn = new TableColumn<>("Track Title");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("trackTitle"));
        table.getColumns().add(firstNameColumn);

        TableColumn lastNameColumn = new TableColumn<>("Album");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("album"));
        table.getColumns().add(lastNameColumn);

        TableColumn emailColumn = new TableColumn<>("Artist");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));
        table.getColumns().add(emailColumn);

        root.getChildren().add(table);

        VBox boxOfButtons = new VBox(10);

        Button[] myButtons = new Button[5];

        myButtons[0] = new Button("Button number one");
        myButtons[0].setPrefSize(200, 50);
        myButtons[0].setOnAction((ActionEvent ae) -> doSomething());

        myButtons[1] = new Button("Button number two");
        myButtons[1].setPrefSize(200, 50);
        myButtons[1].setOnAction((ActionEvent ae) -> doSomething());

        myButtons[2] = new Button("Button number three");
        myButtons[2].setPrefSize(200, 50);
        myButtons[2].setOnAction((ActionEvent ae) -> doSomething());

        myButtons[3] = new Button("Button number four");
        myButtons[3].setPrefSize(200, 50);
        myButtons[3].setOnAction((ActionEvent ae) -> doSomething());

        myButtons[4] = new Button("Button number five");
        myButtons[4].setPrefSize(200, 50);
        myButtons[4].setOnAction((ActionEvent ae) -> doSomething());

        boxOfButtons.getChildren().addAll(myButtons);

        root.getChildren().add(boxOfButtons);


    }

    ObservableList<Track> tracks = FXCollections.observableArrayList(
            new Track("Losing My Edge", "LCD Soundsystem", "LCD Soundsystem"),
            new Track("Isabella", "Johnson", ""),
            new Track("Ethan", "Williams", ""),
            new Track("Emma", "Jones", ""),
            new Track("Michael", "Brown", "")
    );

    private void doSomething() {
    }

    public static void main(String[] args) {
        launch(args);
    }
}