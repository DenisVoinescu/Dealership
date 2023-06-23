import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class Service {
    public Service() {

    }
    private Parent root;
    private Scene scene;
    Stage stage;
    public static void showAlert(Alert.AlertType alertType, String message, String iconJPG) {
        Alert alert = new Alert(alertType);
        alert.setGraphic(null);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(message);
        ImageView icon = new ImageView(iconJPG);
        icon.setFitHeight(48);
        icon.setFitWidth(52);
        alert.getDialogPane().setGraphic(icon);
        alert.show();
    }
    public  void changeSceneTo(String sceneName, ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(sceneName));
        root = loader.load();
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
