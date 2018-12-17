package sample.model;

import sample.objects.*;
import java.io.Serializable;

public class Model implements Serializable {

    private static final long serialVersionUID = 1113799434508676095L;

    public static final String
            LOGIN = "login",
            LOGOUT = "logout",
            REGISTER = "register",
            USER = "user",
            COFE = "cofe",
            UPDATE_ALL = "update_all",
            VIEW_USERS = "view_users",
            BLOCK_USERS = "block_users",
            UNLOCK_USERS = "unlock_users",
            VIEW_COFES = "view_cofes",
            ADD_COFE = "add_cofe",
            EDIT_COFE = "edit_cofe",
            DELETE_COFE = "delete_cofe",
            VIEW_STAFF = "view_staff",
            ADD_STAFF = "add_staff",
            EDIT_STAFF = "edit_staff",
            DELETE_STAFF = "delete_staff",
            VIEW_POSITION_BOX = "view_position_box",
            STAFF = "staff",
            VIEW_ORDER = "view_order",
            CONFIRM = "confirm",
            VIEW_TIME = "view_time";


    private String typeObject;
    private User userObject;
    private Cofe cofeObject;
    private Staff staffObject;
    private Order orderObject;
    private Time timeObject;

    public String getTypeObject() {
        return typeObject;
    }

    public void setTypeObject(String typeObject) {
        this.typeObject = typeObject;
    }

    public User getUserObject() {
        return userObject;
    }

    public void setUserObject(User userObject) {
        this.userObject = userObject;
    }

    public Cofe getCofeObject(){
        return cofeObject;
    }

    public void setCofeObject(Cofe cofe) {
        this.cofeObject = cofe;
    }

    public Staff getStaff(){
        return staffObject;
    }

    public void setStaffObject(Staff staff) {
        this.staffObject = staff;
    }

    public void setOrderObject(Order order) {
        this.orderObject = order;
    }

    public Order getOrderObject() {
        return orderObject;
    }

    public Time getTomeObject(){
        return timeObject;
    }
    public void setTimeObject(Time time) {
        this.timeObject = time;
    }
}
