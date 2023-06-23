import entity.CarEntity;
import entity.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class PaymentSceneController {
    @FXML
    private TextField CardNameTextfield;
    @FXML
    private TextField CardNumberTextfield;

    @FXML
    private TextField CardDateTextfield;

    @FXML
    private TextField CVVTextfield;

    UserDAO userDAO = new UserDAO();
    CarDAO carDAO = new CarDAO();
    public void goBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LogInScene.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }
    String cardNameRegex = "[a-zA-Z]{3,}";
    String cardNumberRegex = "\\d{16}";
    String cardDateRegex = "^(0[1-9]|1[0-2])/[0-9]{2}$";
    String CVVRegex = "^\\d{3}$";

    public void SwitchToPaymentCompletedScene(ActionEvent event) throws IOException, SQLException {
        Service service = new Service();

        String cardName = CardNameTextfield.getText();
        String cardNumber = CardNumberTextfield.getText();
        String cardDate = CardDateTextfield.getText();
        String CVV = CVVTextfield.getText();

        boolean validCardName = true;
        boolean validCardNumber = true;
        boolean validCardDate = true;
        boolean validCVV = true;


        if(cardName.isEmpty() || cardNumber.isEmpty() || cardDate.isEmpty() || CVV.isEmpty()) {
            service.showAlert(Alert.AlertType.WARNING, "Please enter all details. ", "error.jpg");
            MyException.cardDetailsInvalid();
            return;
        }
        if(!cardName.matches(cardNameRegex)) {
            validCardName = false;
            service.showAlert(Alert.AlertType.WARNING,"Invalid card name. ","error.jpg");
        }
        if(!cardNumber.matches(cardNumberRegex)) {
            validCardNumber = false;
            service.showAlert(Alert.AlertType.WARNING,"Invalid card number. ","error.jpg");
        }
        if(!cardDate.matches(cardDateRegex)) {
            validCardDate = false;
            service.showAlert(Alert.AlertType.WARNING,"Invalid card expiring date. ","error.jpg");
        }
        if(!CVV.matches(CVVRegex)) {
            validCVV = false;
            service.showAlert(Alert.AlertType.WARNING,"Invalid CVV. ","error.jpg");
        }

        if (validCardName && validCardNumber && validCardDate && validCVV) {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction transaction = null;

            try {
                transaction = entityManager.getTransaction();
                transaction.begin();

                int userID = userDAO.readUserByUsername(UserFileStorage.retrieveLoggedInUsername()).getUserid();
                int carID = CarFileStorage.retrieveCarId();

                CarEntity car = carDAO.getCarById(carID);
                CarEntity newCar = new CarEntity();
                newCar.setBrandName(car.getBrandName());
                newCar.setModel(car.getModel());
                newCar.setPrice(car.getPrice());
                newCar.setYearFabrication(car.getYearFabrication());
                newCar.setKilometerCount(car.getKilometerCount());

                newCar.setOwnerID(userID);

                entityManager.persist(newCar);

                UserEntity user = entityManager.find(UserEntity.class, userID);
                user.getCars().add(newCar);
                System.out.println(user.getUsername()+" purchased a new car: "+newCar.getBrandName()+" "+newCar.getModel()+" "+newCar.getYearFabrication()+" for the price of: "+newCar.getPrice());

                transaction.commit();
            } catch (Exception e) {
                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();
                }
                e.printStackTrace();
            } finally {
                entityManager.close();
            }
            service.changeSceneTo("PaymentCompletedScene.fxml", event);
        }






        }




    }

