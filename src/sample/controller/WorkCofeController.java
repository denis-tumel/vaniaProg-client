package sample.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import sample.Client;
import sample.model.Model;
import sample.objects.Cofe;
import sample.objects.User;
import sample.utils.DialogManager;

import java.io.IOException;
import java.util.ArrayList;

public class WorkCofeController {
    public Button btnAdd;
    public Button btnDelete;
    public Button btnEdit;
    public TableView<Cofe> table;
    public TableColumn<Cofe, String> name;
    public TableColumn<Cofe, String> price;
    public TextField nameF;
    public TextField priceF;

    private Cofe cofe;
    private Cofe selectedCofe;
    private Model modelObj;

    @FXML
    public void initialize() {
        name.setCellValueFactory(new PropertyValueFactory<Cofe, String>("name"));
        price.setCellValueFactory(new PropertyValueFactory<Cofe, String>("price"));

        Model modelObj = new Model();
        User user = new User();
        user.setType(Model.VIEW_COFES);

        modelObj.setTypeObject(Model.USER);
        modelObj.setUserObject(user);

        try {
            Client.getOutputStream().writeObject(modelObj);
            Client.getOutputStream().flush();

            ArrayList<Cofe> cofes = (ArrayList<Cofe>) Client.getInputStream().readObject();

            table.setItems(FXCollections.observableArrayList(cofes));
            table.refresh();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    selectedCofe = (Cofe) table.getSelectionModel().getSelectedItem();
                    nameF.setText(selectedCofe.getName());
                    priceF.setText(String.valueOf(selectedCofe.getPrice()));
                }
            }
        });
    }

    public void ActionPressed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();

        if (!(source instanceof Button)) {
            return;
        }
        Button clickedButton = (Button) source;
        Cofe selectedCofe = (Cofe) table.getSelectionModel().getSelectedItem();

        switch (clickedButton.getId()) {
            case "btnAdd":
                if (nameF.getText().equals("") || priceF.getText().equals("")) {
                    DialogManager.showErrorDialog("ошибка", "Заполните поля!");
                } else {
                    cofe = new Cofe();
                    cofe.setType(Model.ADD_COFE);
                    cofe.setName(nameF.getText());
                    cofe.setPrice(Integer.parseInt(priceF.getText()));

                    modelObj = new Model();
                    modelObj.setTypeObject(Model.COFE);
                    modelObj.setCofeObject(cofe);
                    System.out.println("obj - "+modelObj.getCofeObject().getName());
                    try {
                        Client.getOutputStream().writeObject(modelObj);
                        Client.getOutputStream().flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    initialize();
                    nameF.clear();
                    priceF.clear();
                }
                break;
            case "btnEdit":
                if (selectedCofe != null) {
                    cofe = selectedCofe;
                    cofe.setType(Model.EDIT_COFE);
                    cofe.setName(nameF.getText());
                    cofe.setPrice(Integer.parseInt(priceF.getText()));

                    modelObj = new Model();
                    modelObj.setTypeObject(Model.COFE);
                    modelObj.setCofeObject(cofe);

                    try {
                        Client.getOutputStream().writeObject(modelObj);
                        Client.getOutputStream().flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    initialize();
                    nameF.clear();
                    priceF.clear();
                } else
                    DialogManager.showErrorDialog("ошибка", "Выберите продукт из таблицы!");
                break;
            case "btnDelete":
                if (selectedCofe != null) {
                    cofe = selectedCofe;
                    cofe.setType(Model.DELETE_COFE);

                    modelObj = new Model();
                    modelObj.setTypeObject(Model.COFE);
                    modelObj.setCofeObject(cofe);

                    try {
                        Client.getOutputStream().writeObject(modelObj);
                        Client.getOutputStream().flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    initialize();
                    nameF.clear();
                    priceF.clear();
                } else
                    DialogManager.showErrorDialog("ошибка", "Выберите продукт из таблицы!");
                break;
        }
    }
}
