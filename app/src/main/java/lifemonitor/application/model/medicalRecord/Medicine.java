package lifemonitor.application.model.medicalRecord;

import java.io.Serializable;

/**
 * Created by leaf on 22/10/14.
 */
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class Medicine implements Serializable {
    private int id;
    private String name;
    private String dosage;
    private Shape shape;
    private HowToTake how_to_take;
    private DangerLevel danger_level;
    private int dangerous;

    public Medicine(){}

    public Medicine(int id) {
        this.id = id;
    }

    public Medicine(String name, Shape shape, HowToTake how_to_take, DangerLevel danger_level) {
        this.name = name;
        this.how_to_take = how_to_take;
        this.shape = shape;
        this.danger_level = danger_level;
    }

    public Medicine(int id, String name, Shape shape, HowToTake how_to_take, DangerLevel danger_level) {
        this.id = id;
        this.name = name;
        this.how_to_take = how_to_take;
        this.shape = shape;
        this.danger_level = danger_level;
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

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public int getDangerous() {
        return dangerous;
    }

    public void setDangerous(int dangerous) {
        this.dangerous = dangerous;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        // Cast this to Object, because of a reported Android Studio bug
        if (o == null || ((Object) this).getClass() != o.getClass()) return false;

        Medicine medicine = (Medicine) o;

        if (dangerous != medicine.dangerous) return false;
        if (id != medicine.id) return false;
        if (danger_level != medicine.danger_level) return false;
        if (dosage != null ? !dosage.equals(medicine.dosage) : medicine.dosage != null)
            return false;
        if (how_to_take != medicine.how_to_take) return false;
        if (name != null ? !name.equals(medicine.name) : medicine.name != null) return false;
        if (shape != medicine.shape) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + shape.hashCode();
        result = 31 * result + how_to_take.hashCode();
        result = 31 * result + danger_level.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return name;
    }
}
