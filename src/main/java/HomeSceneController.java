import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
public class HomeSceneController {
    private Parent root;
    private Scene scene;
    Stage stage;
    @FXML
    private TextField UsernameTextfield;
    @FXML
    private PasswordField PasswordTextfield;
    Service service = new Service();
    UserDAO userDAO = new UserDAO();
    UserFileStorage userFileStorage = new UserFileStorage();

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
    EntityManager entityManager = entityManagerFactory.createEntityManager();



    public void SwitchToLogInScene(ActionEvent event) throws Exception {

        String username = UsernameTextfield.getText();
        String password = PasswordTextfield.getText();

        long count = userDAO.exists(username, password);

        if (count > 0) {
            // Username and password exist in the database
            userFileStorage.storeLoggedInUsername(username);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LogInScene.fxml"));
            root = loader.load();
            LogInSceneController LogInSceneController = loader.getController();
            LogInSceneController.displayName(username);
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            stage.setScene(scene);
            stage.show();

        } else if (username.isEmpty()) {
            service.showAlert(Alert.AlertType.WARNING, "Please enter username. ",null);
        } else if (password.isEmpty()) {
            service.showAlert(Alert.AlertType.WARNING, "Please enter password. ",null);
        }
        else {
            // Username and password do not exist in the database
            service.showAlert(Alert.AlertType.WARNING, "Wrong username / password. ","error.jpg");
            MyException.userDoesNotExist();
        }
    }
    public void SwitchToRegisterScene(ActionEvent event) throws IOException {
        service.changeSceneTo("RegisterScene.fxml", event);
    }
    public void exit(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.control.Button) event.getSource()).getScene().getWindow();

        stage.close();
    }


}






