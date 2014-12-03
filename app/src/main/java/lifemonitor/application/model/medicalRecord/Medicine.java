package lifemonitor.application.model.medicalRecord;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by leaf on 22/10/14.
 */
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class Medicine {
    private int id;
    private String name;
    private Shape shape;
    private HowToTake how_to_take;
    private DangerLevel danger_level;

    public Medicine(){}

    @JsonCreator
    public Medicine(int id, String name, String shape, String how_to_take, int danger_level) {
        this.id = id;
        this.name = name;
        // TODO : add missing attributes
    }

    public Medicine(int id) {
        this.id = id;
    }

    public Medicine(String name, Shape shape, HowToTake how_to_take, DangerLevel danger_level) {
        this.name = name;
        this.how_to_take = how_to_take;
        this.shape = shape;
        this.danger_level = danger_level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;

    }

    public HowToTake getHow_to_take() {
        return how_to_take;
    }

    public void setHow_to_take(HowToTake how_to_take) {
        this.how_to_take = how_to_take;
    }

    public DangerLevel getDanger_level() {
        return danger_level;
    }

    public void setDanger_level(DangerLevel danger_level) {
        this.danger_level = danger_level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
