package sample.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.Client;
import sample.config.Const;
import sample.model.Model;
import sample.objects.Cofe;
import sample.objects.Time;
import sample.objects.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class OrderController {
    public ComboBox<Time> time;
    public ComboBox<String> countCofe;
    public TableView<Cofe> table;
    public TableColumn<Cofe, String> name;
    public TableColumn<Cofe, String> price;
    private User user;
    static Stage confirmStage;
    private ConfirmController confirmController;
    private ArrayList<String> countCofes = new ArrayList<String>(Arrays.asList(
            "1","2","3","4","5"
    ));

    private ArrayList<String> times = new ArrayList<String>(Arrays.asList(
            "8 : 00","9 : 00","10 : 00","11 : 00","12 : 00","13 : 00","14 : 00","15 : 00","16 : 00","17 : 00"
    ));

    @FXML
    public void initialize(){
        name.setCellValueFactory(new PropertyValueFactory<Cofe, String>("name"));
        price.setCellValueFactory(new PropertyValueFactory<Cofe, String>("price"));
        countCofe.setItems(FXCollections.observableArrayList(countCofes));


        viewCofe();
        viewTime();
    }

    private void viewTime() {
        Model modelObj = new Model();
        user = new User();
        user.setType(Model.VIEW_TIME);

        modelObj.setTypeObject(Model.USER);
        modelObj.setUserObject(user);

        try {
            Client.getOutputStream().writeObject(modelObj);
            Client.getOutputStream().flush();

            ArrayList<Time> times = (ArrayList<Time>) Client.getInputStream().readObject();

            time.setItems(FXCollections.observableArrayList(times));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void viewCofe() {
        Model modelObj = new Model();
        user = new User();
        user.setType(Model.VIEW_COFES);

        modelObj.setTypeObject(Model.USER);
        modelObj.setUserObject(user);

        try {
            Client.getOutputStream().writeObject(modelObj);
            Client.getOutputStream().flush();

            ArrayList<Cofe> cofes = (ArrayList<Cofe>) Client.getInputStream().readObject();

            table.setItems(FXCollections.observableArrayList(cofes));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setInformation(User user) {
        this.user = user;
    }

    public void ActionOrder(ActionEvent actionEvent) {
        Time times = (Time) time.getSelectionModel().getSelectedItem();
        String count = (String) countCofe.getSelectionModel().getSelectedItem();
        Cofe cofe = (Cofe) table.getSelectionModel().getSelectedItem();

        FXMLLoader loader = new FXMLLoader();
        try {
            if (confirmStage == null) {
                loader.setLocation(getClass().getResource(Const.PATH_CONFIRM_VIEW));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                confirmStage = new Stage();

                confirmStage.setTitle("страница подтверждения");
                confirmStage.setScene(scene);
                confirmController = (ConfirmController) loader.getController();
            }
            confirmController.setInformation(user, times, count, cofe);
            ClientController.orderStage.close();
            confirmStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
