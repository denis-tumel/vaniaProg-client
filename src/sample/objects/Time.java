package sample.objects;

import java.io.Serializable;

public class Time implements Serializable {

    private static final long serialVersionUID = 1113799434508676095L;

    private String type;
    private String name;
    private int id;

    @Override
    public String toString() {
        return name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
