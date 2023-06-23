import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;


public class AvailableCarsSceneController {
    @FXML
    private VBox carContainer;
    Service service = new Service();
        @FXML
        private ImageView MechanicImageView;
        public ImageView getMechanicImageView() {
            return MechanicImageView;
        }

    public void setCarContainer(StackPane carContainer) {
        this.carContainer.getChildren().setAll(carContainer.getChildren());
    }
    public void GoBackToLogInScene(ActionEvent event) throws IOException {
            service.changeSceneTo("LogInScene.fxml",event);
    }
}
