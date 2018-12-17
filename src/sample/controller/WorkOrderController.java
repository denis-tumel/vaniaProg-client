package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.Client;
import sample.config.Const;
import sample.model.Model;
import sample.objects.*;
import sample.utils.DialogManager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WorkOrderController {
    public TableView<Order> table;
    public TableColumn<Order, String> time;
    public TableColumn<Order, String> countCoffe;
    public TableColumn<Order, String> user;
    public TableColumn<Order, String> cofe;

    private Model modelObj;
    private Stage chartStage;
    private ChartController chartController;

    @FXML
    public void initialize(){
        time.setCellValueFactory(new PropertyValueFactory<Order, String>("time"));
        countCoffe.setCellValueFactory(new PropertyValueFactory<Order, String>("countCofe"));
        user.setCellValueFactory(new PropertyValueFactory<Order, String>("userName"));
        cofe.setCellValueFactory(new PropertyValueFactory<Order, String>("cofe"));

        modelObj = new Model();
        User user = new User();
        user.setType(Model.VIEW_ORDER);

        modelObj.setTypeObject(Model.USER);
        modelObj.setUserObject(user);

        try {
            Client.getOutputStream().writeObject(modelObj);
            Client.getOutputStream().flush();

            ArrayList<Order> orders = (ArrayList<Order>) Client.getInputStream().readObject();

            table.setItems(FXCollections.observableArrayList(orders));
            table.refresh();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void ActionViewChart(ActionEvent actionEvent) {
        try {
            if(chartStage == null){
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(Const.PATH_CHART));
                Parent fxmlMain = loader.load();
                chartStage = new Stage();
                chartStage.setTitle("Работа с заказами");
                Scene scene = new Scene(fxmlMain);
                chartStage.setScene(scene);
                chartStage.setResizable(false);
                chartController = loader.getController();
            }
            chartController.setInformation(table.getItems());
            chartStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ActionUpdateOrder(ActionEvent actionEvent) {
        Cofe cofe = new Cofe();
        modelObj = new Model();
        cofe.setType(Model.UPDATE_ALL);
        modelObj.setTypeObject(Model.COFE);
        modelObj.setCofeObject(cofe);

        try {
            Client.getOutputStream().writeObject(modelObj);
            Client.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        initialize();
        DialogManager.showInfoDialog("инфо", "Обновление прошло успешно!");
    }

    public void ActionWriteInfoInFile(ActionEvent actionEvent) {
        saveInformationInFile(table.getItems());
    }

    private void saveInformationInFile(ObservableList<Order> items) {
        int count = 0;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Const.INFO_FILE, true))) {
            for (Order order : items) {
                count++;
                String out = count + " время:" + order.getTime() + ",\nкол-во кофе: " + order.getCountCofe() + ",\nпользователь: " + order.getUserName() + ",\nназвание кофе: " + order.getCofe().getName() + " \n";
                writer.append(out);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        DialogManager.showInfoDialog("инфо", "Информация успешно записана!");
    }
}
