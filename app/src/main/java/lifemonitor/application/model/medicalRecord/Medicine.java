package lifemonitor.application.model.medicalRecord;

/**
 * Created by leaf on 22/10/14.
 */
public class Medicine {
    private String name;
    private Shape shape;
    private HowToConsume HowToConsume;
    private DangerLevel dangerLevel;

    public Medicine(String name, Shape shape, HowToConsume howToConsume, DangerLevel dangerLevel) {
        this.name = name;
        HowToConsume = howToConsume;
        this.shape = shape;
        this.dangerLevel = dangerLevel;
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

    public HowToConsume getHowToConsume() {
        return HowToConsume;
    }

    public void setHowToConsume(HowToConsume HowToConsume) {
        this.HowToConsume = HowToConsume;
    }

    public DangerLevel getDangerLevel() {
        return dangerLevel;
    }

    public void setDangerLevel(DangerLevel dangerLevel) {
        this.dangerLevel = dangerLevel;
    }

}
