package lifemonitor.application.model.medicalRecord;

import java.io.Serializable;

/**
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public class Doctor implements Serializable {

    private static final long serialVersionUID = -3516580140054817293L;
    private int id;
    private String name;

    public Doctor() {}

    public Doctor(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
