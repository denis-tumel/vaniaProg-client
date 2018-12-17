package sample.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.Client;
import sample.config.Const;

import java.io.IOException;

import static sample.Client.mainStage;

public class MainController {
    public static Stage authenticationStage;
    public static Stage registrationStage;

    public void ActionLogin(ActionEvent actionEvent) {
        try {
            if (authenticationStage == null) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(Const.PATH_AUTHENTICATION_VIEW));
                Parent fxmlAuthentication = loader.load();
                Scene scene = new Scene(fxmlAuthentication);
                authenticationStage = new Stage();
                authenticationStage.setTitle("Авторизация");
                authenticationStage.setResizable(false);
                authenticationStage.setScene(scene);
                authenticationStage.initModality(Modality.WINDOW_MODAL);
                authenticationStage.initOwner(mainStage);
            }
            mainStage.hide();
            authenticationStage.show();
            eventClose(authenticationStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ActionRegister(ActionEvent actionEvent) {
        try {
            if (registrationStage == null) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(Const.PATH_REGISTRATION_VIEW));
                Parent fxmlAuthentication = loader.load();
                Scene scene = new Scene(fxmlAuthentication);
                registrationStage = new Stage();
                registrationStage.setTitle("Регистрация");
                registrationStage.setResizable(false);
                registrationStage.setScene(scene);
                registrationStage.initModality(Modality.WINDOW_MODAL);
                registrationStage.initOwner(mainStage);
            }
            mainStage.hide();
            registrationStage.show();
            eventClose(registrationStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void eventClose(Stage stage) {
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                Client.close();
            }
        });
    }
}
