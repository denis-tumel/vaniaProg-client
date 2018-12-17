package sample.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import sample.Client;
import sample.config.Const;
import sample.model.Model;
import sample.objects.Order;
import sample.objects.User;

import java.io.IOException;
import java.util.ArrayList;

public class ClientController {
    public TextArea listOrder;
    private User user;
    static Stage orderStage;
    private OrderController orderController;
    private Model modelObj;
    @FXML
    public void initialize(){

    }

    public void setInfo(User user) {
        this.user = user;
        ArrayList<Order> order;
        modelObj = new Model();
        user.setType(Model.VIEW_ORDER);

        modelObj.setTypeObject(Model.USER);
        modelObj.setUserObject(user);

        try {
            Client.getOutputStream().writeObject(modelObj);
            Client.getOutputStream().flush();

            order = (ArrayList<Order>) Client.getInputStream().readObject();

            listOrder.clear();
            for (Order info : order){
                if(info.getUserID() == user.getId()){
                    String information = "\n\tВремя - "+info.getTime()+"\nКоличество кофе - "+info.getCountCofe()+"\nКофе - "+info.getCofe().getName()+
                            "\nЦена "+info.getCofe().getPrice()+
                            "\n____________________________";
                    listOrder.appendText(information);
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void ActionOrderCofe(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();
        try {
            if (orderStage == null) {
                loader.setLocation(getClass().getResource(Const.PATH_ORDER_VIEW));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                orderStage = new Stage();

                orderStage.setTitle("страница заказа");
                orderStage.setScene(scene);
                orderController = (OrderController) loader.getController();
            }
            orderController.setInformation(user);
            orderStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
