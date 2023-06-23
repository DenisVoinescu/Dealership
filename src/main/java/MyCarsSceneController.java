import entity.CarEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class MyCarsSceneController {
    Service service = new Service();

    @FXML
    private ListView<AnchorPane> carListView;

    public void setCarList(List<CarEntity> cars) {
        ObservableList<AnchorPane> carList = FXCollections.observableArrayList();
        for (CarEntity car : cars) {
            carList.add(createCarPane(car));
        }
        carListView.setItems(carList);
    }

    private AnchorPane createCarPane(CarEntity car) {
        AnchorPane carPane = new AnchorPane();
        carPane.setPrefSize(320, 160);
       String username = UserFileStorage.retrieveLoggedInUsername();
        String imagePath = car.getImagePath();
        Image carImage = new Image("car.png");

        ImageView carImageView = new ImageView(carImage);
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

        AnchorPane.setLeftAnchor(carImageView, 10.0);
        AnchorPane.setTopAnchor(carImageView, 10.0);

        AnchorPane.setLeftAnchor(labelsContainer, 180.0);
        AnchorPane.setTopAnchor(labelsContainer, 10.0);

        carPane.getChildren().addAll(carImageView, labelsContainer);

        return carPane;
    }


    public void goBackToLogInScene(ActionEvent event) throws IOException {
        service.changeSceneTo("LogInScene.fxml", event);
    }
}
