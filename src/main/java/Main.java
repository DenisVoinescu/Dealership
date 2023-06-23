import entity.CarEntity;
import entity.UserEntity;
import jakarta.persistence.*;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class Main extends Application {



    public static void main(String[] args) {

        UserDAO userDAO = new UserDAO();
        CarDAO carDAO = new CarDAO();
        UserEntity user = new UserEntity();
        CarEntity car = new CarEntity();


            /*EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();*/
            carDAO.initializeCars();


      launch(args);

    }

    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeScene.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root,500,400);
        stage.setTitle("Online Dealership");
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        stage.setResizable(false);
        stage.setWidth(1000);
        stage.setHeight(700);
        stage.setScene(scene);
        stage.getIcons().add(new Image("icon.jpg"));
        stage.show();
}
    }



