package sample.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import sample.Client;
import sample.model.Model;
import sample.objects.Position;
import sample.objects.Staff;
import sample.objects.User;
import sample.utils.DialogManager;

import java.io.IOException;
import java.util.ArrayList;

public class WorkStaffController {
    public Button btnAdd;
    public Button btnDelete;
    public Button btnEdit;
    public TableView<Staff> table;
    public TableColumn<Staff, String> name;
    public TableColumn<Staff, String> surname;
    public TableColumn<Staff, String> position;
    public TableColumn<Staff, String> salary;
    public TextField nameF;
    public TextField surnameF;
    public TextField priceF;
    public ComboBox<Position> positionBox;
    private Staff selectedStaff;
    private Staff staff;
    private Model modelObj;

    @FXML
    public void initialize(){
        name.setCellValueFactory(new PropertyValueFactory<Staff, String>("name"));
        surname.setCellValueFactory(new PropertyValueFactory<Staff, String>("surname"));
        position.setCellValueFactory(new PropertyValueFactory<Staff, String>("positionName"));
        salary.setCellValueFactory(new PropertyValueFactory<Staff, String>("salary"));

        modelObj = new Model();
        User user = new User();
        user.setType(Model.VIEW_STAFF);

        modelObj.setTypeObject(Model.USER);
        modelObj.setUserObject(user);

        try {
            Client.getOutputStream().writeObject(modelObj);
            Client.getOutputStream().flush();

            ArrayList<Staff> staffs = (ArrayList<Staff>) Client.getInputStream().readObject();

            table.setItems(FXCollections.observableArrayList(staffs));
            table.refresh();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        modelObj = new Model();
        user = new User();
        user.setType(Model.VIEW_POSITION_BOX);

        modelObj.setTypeObject(Model.USER);
        modelObj.setUserObject(user);

        try {
            Client.getOutputStream().writeObject(modelObj);
            Client.getOutputStream().flush();

            ArrayList<Position> positionsBox = (ArrayList<Position>) Client.getInputStream().readObject();

            positionBox.setItems(FXCollections.observableArrayList(positionsBox));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    selectedStaff = (Staff) table.getSelectionModel().getSelectedItem();
                    nameF.setText(selectedStaff.getName());
                    surnameF.setText(selectedStaff.getSurname());
                    positionBox.getItems().add(selectedStaff.getPosition());
                    priceF.setText(String.valueOf(selectedStaff.getSalary()));
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
        Staff selectedCofe = (Staff) table.getSelectionModel().getSelectedItem();

        switch (clickedButton.getId()) {
            case "btnAdd":
                if (nameF.getText().equals("") || priceF.getText().equals("")) {
                    DialogManager.showErrorDialog("ошибка", "Заполните поля!");
                } else {
                    staff = new Staff();
                    staff.setType(Model.ADD_STAFF);
                    staff.setName(nameF.getText());
                    staff.setSurname(surnameF.getText());
                    staff.setPosition(positionBox.getValue());
                    staff.setSalary(Integer.parseInt(priceF.getText()));

                    modelObj = new Model();
                    modelObj.setTypeObject(Model.STAFF);
                    modelObj.setStaffObject(staff);

                    try {
                        Client.getOutputStream().writeObject(modelObj);
                        Client.getOutputStream().flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    initialize();
                    nameF.clear();
                    surnameF.clear();
                    priceF.clear();
                }
                break;
            case "btnEdit":
                if (selectedStaff != null) {
                    staff = selectedCofe;
                    staff.setType(Model.EDIT_STAFF);
                    staff.setName(nameF.getText());
                    staff.setSurname(surnameF.getText());
                    staff.setPosition(positionBox.getValue());
                    staff.setSalary(Integer.parseInt(priceF.getText()));

                    modelObj = new Model();
                    modelObj.setTypeObject(Model.STAFF);
                    modelObj.setStaffObject(staff);

                    try {
                        Client.getOutputStream().writeObject(modelObj);
                        Client.getOutputStream().flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    initialize();
                    nameF.clear();
                    surnameF.clear();
                    priceF.clear();
                } else
                    DialogManager.showErrorDialog("ошибка", "Выберите персону из таблицы!");
                break;
            case "btnDelete":
                if (selectedStaff != null) {
                    staff = selectedCofe;
                    staff.setType(Model.DELETE_STAFF);

                    modelObj = new Model();
                    modelObj.setTypeObject(Model.STAFF);
                    modelObj.setStaffObject(staff);

                    try {
                        Client.getOutputStream().writeObject(modelObj);
                        Client.getOutputStream().flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    initialize();
                    nameF.clear();
                    surnameF.clear();
                    priceF.clear();
                } else
                    DialogManager.showErrorDialog("ошибка", "Выберите персону из таблицы!");
                break;
        }
    }
}
