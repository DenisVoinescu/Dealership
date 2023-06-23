import javafx.event.ActionEvent;

import java.io.IOException;

public class PaymentCompletedSceneController {
    Service service = new Service();
    public void SwitchToLogInScene(ActionEvent event) throws IOException {
        service.changeSceneTo("LogInScene.fxml",event);
    }
}
