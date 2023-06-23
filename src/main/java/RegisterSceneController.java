import entity.UserEntity;
import jakarta.persistence.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RegisterSceneController {
    @FXML
            private CheckBox notARobbotCheck;
    LocalDateTime now = LocalDateTime.now();
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();
    Service service = new Service();
    @FXML
    private TextField RegPasswordTextfield;
    @FXML
    private TextField RegConPasswordTextfield;
    @FXML
    private TextField RegUsernameTextfield;
    @FXML
    private  TextField RegEmailTextfield;

    String emailRegex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    String usernameRegex = "^[a-zA-Z0-9]{5,}$";
    String passwordRegex = "^.{5,}$";

    public boolean passwordsMatch(String password, String confirmPassword) {
        if(password.equals(confirmPassword))
            return true;
        else
            return false;
    }
    public void SwitchToHomeScene(ActionEvent event) throws IOException {
        service.changeSceneTo("HomeScene.fxml",event);
    }


    public void CompleteRegistration(ActionEvent event) throws IOException {
           String username = RegUsernameTextfield.getText();
           String email = RegEmailTextfield.getText();
           String password = RegPasswordTextfield.getText();
           String conPassword = RegConPasswordTextfield.getText();

           String queryStringUsername = "SELECT COUNT(u) FROM UserEntity u WHERE u.username = :username ";
           String queryStringEmail = "SELECT COUNT(u) FROM UserEntity u WHERE u.email = :email ";
           TypedQuery<Long> queryUsername = entityManager.createQuery(queryStringUsername, Long.class);
           TypedQuery<Long> queryEmail = entityManager.createQuery(queryStringEmail, Long.class);
           queryUsername.setParameter("username", username);
           queryEmail.setParameter("email", email);

           Long countUsername = queryUsername.getSingleResult();
           Long countEmail = queryEmail.getSingleResult();

           boolean avlbUsername = true;
           boolean avlbEmail = true;
           boolean passwordsMatch = true;
           boolean validUsername = true;
           boolean validEmail = true;
           boolean validPassword = true;



        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || conPassword.isEmpty()) {
            service.showAlert(Alert.AlertType.WARNING, "Please enter all details. ","error.jpg");
            return;
        }

         if (countUsername > 0) {
               // Username already exists in the database

               avlbUsername = false;
             service.showAlert(Alert.AlertType.WARNING, "Username already exists. ","error.jpg");
           }
           if (countEmail > 0) {
               // Email already exists in the database

               MyException.emailInvalid();
               avlbEmail = false;
               service.showAlert(Alert.AlertType.WARNING, "E-Mail already exists. ","error.jpg");

           }

           if (!passwordsMatch(password, conPassword)) {
               MyException.passwordsDontMatch();
               passwordsMatch = false;
               service.showAlert(Alert.AlertType.WARNING, "Passwords do not match. ","error.jpg");
           }
               if (!username.matches(usernameRegex)){
                   MyException.usernameInvalid();
                   validUsername = false;
                   service.showAlert(Alert.AlertType.WARNING, "Username must be at least 5 characters long and not contain any symbol. ","error.jpg");
               }

               if (!password.matches(passwordRegex)) {
                   MyException.passwordInvalid();
                   validPassword = false;
                   service.showAlert(Alert.AlertType.WARNING, "Password must be at least 5 characters long. ","error.jpg");
               }
               if(!email.matches(emailRegex)) {
                   validEmail = false;
                   service.showAlert(Alert.AlertType.WARNING, "E-Mail must be valid","error.jpg");
               }

           if(avlbUsername && avlbEmail && passwordsMatch && validEmail && validUsername && validPassword && notARobbotCheck.isSelected()){
               // The user is eligible for registration

               try {
                   transaction.begin();

                   UserEntity User = new UserEntity(email, username, password);
                   User.setRegisterDate(now.toString());
                   entityManager.persist(User);

                   service.showAlert(Alert.AlertType.INFORMATION, "You registered succesfully! You can now log in! ","check.png");

                   service.changeSceneTo("HomeScene.fxml", event);

                   transaction.commit();

               }
               finally {
                   if (transaction.isActive()) {
                       transaction.rollback();
                   }
                   entityManager.close();
                   entityManagerFactory.close(); }
           }
       }
       }




