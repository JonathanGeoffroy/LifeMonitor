package lifemonitor.application.model.medicalRecord;

import java.util.List;

/**
 * @author Jonathan Geoffroy
 */
public class MedicalRecord {
    private int id;
    private List<Allergy> allergies;
    private List<Illness> illnesses;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Allergy> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<Allergy> allergies) {
        this.allergies = allergies;
    }

    public List<Illness> getIllnesses() {
        return illnesses;
    }

    public void setIllnesses(List<Illness> illnesses) {
        this.illnesses = illnesses;
    }

    public MedicalRecord() {}
}
