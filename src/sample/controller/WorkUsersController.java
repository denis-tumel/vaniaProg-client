package sample.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Client;
import sample.model.Model;
import sample.objects.User;
import sample.utils.DialogManager;

import java.io.IOException;
import java.util.ArrayList;

public class WorkUsersController {
    public TableColumn<User, Integer> role;
    public TableColumn<User, String> name;
    public TableColumn<User, String> surname;
    public TableColumn<User, String> email;
    public TableColumn<User, String> password;
    public TableView<User> table;

    private Model modelObj;
    private User user;

    @FXML
    public void initialize() {
        role.setCellValueFactory(new PropertyValueFactory<User, Integer>("role"));
        name.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        surname.setCellValueFactory(new PropertyValueFactory<User, String>("surname"));
        email.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        password.setCellValueFactory(new PropertyValueFactory<User, String>("password"));

        Model modelObj = new Model();
        User user = new User();
        user.setType(Model.VIEW_USERS);

        modelObj.setTypeObject(Model.USER);
        modelObj.setUserObject(user);

        try {
            Client.getOutputStream().writeObject(modelObj);
            Client.getOutputStream().flush();

            ArrayList<User> users = (ArrayList<User>) Client.getInputStream().readObject();

            table.setItems(FXCollections.observableArrayList(users));
            table.refresh();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void ActionBlock(ActionEvent actionEvent) {
        User selectedUser = (User) table.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            user = selectedUser;
            user.setType(Model.BLOCK_USERS);
            modelObj = new Model();
            modelObj.setTypeObject(Model.USER);
            modelObj.setUserObject(user);
            try {
                Client.getOutputStream().writeObject(modelObj);
                Client.getOutputStream().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            DialogManager.showInfoDialog("инфо", "Пользователь заблокирован!");
            initialize();
        } else
            DialogManager.showErrorDialog("ошибка", "Выберите пользователя из таблицы!");
    }

    public void ActionUnlock(ActionEvent actionEvent) {
        User selectedUser = (User) table.getSelectionModel().getSelectedItem();
        if (selectedUser != null){
            user = selectedUser;
            user.setType(Model.UNLOCK_USERS);
            modelObj = new Model();
            modelObj.setTypeObject(Model.USER);
            modelObj.setUserObject(user);
            try {
                Client.getOutputStream().writeObject(modelObj);
                Client.getOutputStream().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            DialogManager.showInfoDialog("инфо", "Пользователь разаблокирован!");
            initialize();
        }
        else
            DialogManager.showErrorDialog("ошибка", "Выберите пользователя из таблицы!");
    }
}
