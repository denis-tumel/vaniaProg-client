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
import static sample.controller.MainController.authenticationStage;

public class LoginController {

    public PasswordField passwordF;
    public TextField emailF;

    public static Stage adminStage;
    public static Stage clientStage;
    public static ClientController clientController;

    public void ActionLogin(ActionEvent actionEvent) {
        if(passwordF.getText().equals("") || emailF.getText().equals("")){
            DialogManager.showErrorDialog("ошибка", "Заполните поля!");
        }else{
            User user = new User();
            user.setEmail(emailF.getText().trim());
            user.setPassword(passwordF.getText().trim());
            user.setType(Model.LOGIN);

            Model modelObj = new Model();
            modelObj.setTypeObject(Model.USER);
            modelObj.setUserObject(user);

            try {
                Client.getOutputStream().writeObject(modelObj);
                Client.getOutputStream().flush();

                user = (User) Client.getInputStream().readObject();

                if (user != null) {
                    switch (user.getRole()) {
                        case 1:
                            try {
                                if (adminStage == null) {
                                    FXMLLoader loader = new FXMLLoader();
                                    loader.setLocation(getClass().getResource(Const.PATH_ADMIN_VIEW));
                                    Parent root = loader.load();
                                    Scene scene = new Scene(root);
                                    adminStage = new Stage();

                                    adminStage.setTitle("админ");
                                    adminStage.setResizable(false);
                                    adminStage.setScene(scene);
                                }
                                authenticationStage.hide();
                                adminStage.show();
                                eventClose(adminStage);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            showInformation();
                            clearField();
                            break;
                        case 0:
                            try {
                                if (clientStage == null) {
                                    FXMLLoader loader = new FXMLLoader();
                                    loader.setLocation(getClass().getResource(Const.PATH_CLIENT_VIEW));
                                    Parent root = loader.load();
                                    Scene scene = new Scene(root);
                                    clientStage = new Stage();

                                    clientStage.setTitle("Клиент");
                                    clientStage.setResizable(false);
                                    clientStage.setScene(scene);
                                    clientController = loader.getController();
                                }
                                clientController.setInfo(user);
                                authenticationStage.hide();
                                clientStage.show();
                                eventClose(clientStage);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            showInformation();
                            clearField();
                            break;
                        case -1:
                            clearField();
                            DialogManager.showErrorDialog("ошибка", user.getName()+" вы заблокированы!");
                            break;
                    }
                } else{
                    DialogManager.showErrorDialog("ошибка", "Непрвильный логин или пароль!");
                    clearField();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void showInformation() {
        DialogManager.showInfoDialog("инфо", "Авторизация прошла успешно!");
    }

    private void clearField() {
        emailF.clear();
        passwordF.clear();
    }

    public void ActionBack(ActionEvent actionEvent) {
        authenticationStage.close();
        mainStage.show();
    }

    private static void eventClose(Stage stage) {
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                Client.close();
            }
        });
    }
}
