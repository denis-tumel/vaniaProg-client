package sample.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.Client;
import sample.config.Const;
import sample.model.Model;
import sample.objects.User;
import sample.utils.DialogManager;

import java.io.IOException;

import static sample.Client.mainStage;
import static sample.controller.LoginController.clientStage;
import static sample.controller.MainController.registrationStage;

public class RegistrationController {
    public TextField nameF;
    public TextField surnameF;
    public TextField emailF;
    public PasswordField passwordF;
    private ClientController clientController;
    private User user;

    public void ActionBack(ActionEvent actionEvent) {
        registrationStage.close();
        mainStage.show();
    }

    public void ActionRegister(ActionEvent actionEvent) {
        user = new User(nameF.getText().trim(), surnameF.getText().trim(), emailF.getText().trim(), passwordF.getText().trim(), Model.REGISTER);

        Model modelObj = new Model();
        modelObj.setTypeObject(Model.USER);
        modelObj.setUserObject(user);

        try {
            Client.getOutputStream().writeObject(modelObj);
            Client.getOutputStream().flush();

            user = (User) Client.getInputStream().readObject();

            registrationStage.close();

            if (clientStage == null) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(Const.PATH_CLIENT_VIEW));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                clientStage = new Stage();

                clientStage.setTitle("клиент");
                clientStage.setResizable(false);
                clientStage.setScene(scene);
                clientController = loader.getController();
            }
            clientController.setInfo(user);
            clientStage.show();
            eventClose(clientStage);
            DialogManager.showInfoDialog("инфо", "Регистрация прошла успешно!");

        } catch (IOException | ClassNotFoundException e) {
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
