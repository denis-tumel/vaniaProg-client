package sample.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import sample.Client;
import sample.model.Model;
import sample.objects.Cofe;
import sample.objects.Order;
import sample.objects.Time;
import sample.objects.User;
import sample.utils.DialogManager;

import java.io.IOException;

public class ConfirmController {
    public Label time;
    public Label countCofe;
    public Label nameCofe;
    public Label price;
    private User user;
    private Cofe cofe;
    private Time times;

    public void setInformation(User user, Time times, String count, Cofe cofe) {
        this.times = times;
        this.cofe = cofe;
        this.user = user;
        time.setText(times.getName());
        countCofe.setText(count);
        nameCofe.setText(cofe.getName());
        price.setText(String.valueOf(cofe.getPrice()));
    }

    public void ActionConfirm(ActionEvent actionEvent) {
        Model modelObj = new Model();
        Order order = new Order();
        order.setUserName(user.getName());
        order.setTime(time.getText());
        order.setCountCofe(Integer.parseInt(countCofe.getText()));
        cofe.setType(Model.CONFIRM);
        order.setCofe(cofe);

        modelObj.setTypeObject(Model.COFE);
        modelObj.setOrderObject(order);
        modelObj.setUserObject(user);
        modelObj.setCofeObject(cofe);
        modelObj.setTimeObject(times);

        try {
            Client.getOutputStream().writeObject(modelObj);
            Client.getOutputStream().flush();

            OrderController.confirmStage.close();
            LoginController.clientController.setInfo(user);
            DialogManager.showInfoDialog("инфо", "Бронь прошла успешно!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ActionCancel(ActionEvent actionEvent) {
        OrderController.confirmStage.close();
        ClientController.orderStage.show();
    }
}
