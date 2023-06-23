import entity.CarEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;


public class LogInSceneController {

    CarDAO carDAO = new CarDAO();
    Service service = new Service();
    UserDAO userDAO = new UserDAO();
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
    EntityManager entityManager = entityManagerFactory.createEntityManager();

    public void displayName(String username) {
        service.showAlert(Alert.AlertType.INFORMATION, "Log in successful. Welcome, " + username, "check.png");
    }
    private AnchorPane createCarPane(CarEntity car) {
        AnchorPane carPane = new AnchorPane();
        carPane.setPrefSize(320, 160);

        ImageView carImageView = new ImageView(new Image(car.getImagePath()));
        carImageView.setFitWidth(160);
        carImageView.setFitHeight(160);
        carImageView.setPreserveRatio(true);

        VBox labelsContainer = new VBox();
        labelsContainer.setSpacing(5);
        Label brandLabel = new Label("Brand: " + car.getBrandName());
        Label modelLabel = new Label("Model: " + car.getModel());
        Label yearLabel = new Label("Year: " + car.getYearFabrication());
        Label kmLabel = new Label("Kilometers: " + car.getKilometerCount());
        Label priceLabel = new Label("Price: $" + car.getPrice());
        labelsContainer.getChildren().addAll(brandLabel, modelLabel, yearLabel, kmLabel, priceLabel);
        Button buyNowButton = new Button("Buy now");

        buyNowButton.setOnAction(event -> {
            try {
                CarFileStorage.storeCarId(carDAO.getCarIdByModel(car.getModel()));
                service.changeSceneTo("PaymentScene.fxml", event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });

        AnchorPane.setLeftAnchor(carImageView, 0.0);
        AnchorPane.setTopAnchor(carImageView, 0.0);
        AnchorPane.setLeftAnchor(labelsContainer, 170.0);
        AnchorPane.setTopAnchor(labelsContainer, 0.0);
        AnchorPane.setLeftAnchor(buyNowButton, 170.0);
        AnchorPane.setTopAnchor(buyNowButton, 120.0);
        carPane.getChildren().addAll(carImageView, labelsContainer, buyNowButton);
        return carPane;
    }

    public void SwitchToAvailableCarsScene(ActionEvent event) throws IOException {
        VBox carContainer = new VBox();
        carContainer.setSpacing(10);
        carContainer.setPadding(new Insets(15));

        for (CarEntity car : carDAO.getCars()) {
            AnchorPane carPane = createCarPane(car);
            carPane.setPadding(new Insets(15));
            carContainer.getChildren().add(carPane);
        }

        ScrollPane scrollPane = new ScrollPane(carContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        StackPane stackPane = new StackPane(scrollPane);
        stackPane.setPrefSize(800, 600);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AvailableCarsScene.fxml"));
        Parent root = loader.load();
        AvailableCarsSceneController availableCarsSceneController = loader.getController();
        Image mechanic = new Image("mechanic.jpg");
        availableCarsSceneController.getMechanicImageView().setImage(mechanic);
        availableCarsSceneController.setCarContainer(stackPane);
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);

    }

    public void SwitchToHomeScene(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to log out? ");
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            service.changeSceneTo("HomeScene.fxml", event);
        }

    }
    public void switchToMyCarsScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MyCarsScene.fxml"));
        Parent root = loader.load();
        MyCarsSceneController myCarsSceneController = loader.getController();
        String username = UserFileStorage.retrieveLoggedInUsername();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        List<CarEntity> cars = userDAO.readUserByUsername(username).getCars();
        myCarsSceneController.setCarList(cars);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}



