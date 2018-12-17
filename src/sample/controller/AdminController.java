package sample.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.Client;
import sample.config.Const;

import java.io.IOException;

public class AdminController {

    public static Stage workUsersStage;
    public static Stage workCofeStage;
    public static Stage workStaffStage;
    public static Stage workOrderStage;

    public void ActionViewUsers(ActionEvent actionEvent) {
        try {
            if(workUsersStage == null){
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(Const.PATH_WORK_USERS_VIEW));
                Parent fxmlMain = loader.load();
                workUsersStage = new Stage();
                workUsersStage.setTitle("Работа с пользователями");
                Scene scene = new Scene(fxmlMain);
                workUsersStage.setScene(scene);
                workUsersStage.setResizable(false);
            }
            workUsersStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ActionViewCafe(ActionEvent actionEvent) {
        try {
            if(workCofeStage == null){
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(Const.PATH_WORK_COFE));
                Parent fxmlMain = loader.load();
                workCofeStage = new Stage();
                workCofeStage.setTitle("Работа с кофе");
                Scene scene = new Scene(fxmlMain);
                workCofeStage.setScene(scene);
                workCofeStage.setResizable(false);
            }
            workCofeStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ActionViewStaff(ActionEvent actionEvent) {
        try {
            if(workStaffStage == null){
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(Const.PATH_WORK_STAFF_VIEW));
                Parent fxmlMain = loader.load();
                workStaffStage = new Stage();
                workStaffStage.setTitle("Работа с персоналом");
                Scene scene = new Scene(fxmlMain);
                workStaffStage.setScene(scene);
                workStaffStage.setResizable(false);
            }
            workStaffStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ActionOrderView(ActionEvent actionEvent) {
        try {
            if(workOrderStage == null){
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(Const.PATH_WORK_ORDER_VIEW));
                Parent fxmlMain = loader.load();
                workOrderStage = new Stage();
                workOrderStage.setTitle("Работа с заказами");
                Scene scene = new Scene(fxmlMain);
                workOrderStage.setScene(scene);
                workOrderStage.setResizable(false);
            }
            workOrderStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
