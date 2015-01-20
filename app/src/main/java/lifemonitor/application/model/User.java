package lifemonitor.application.model;

/**
 * A user authenticated in local DB
 */
public class User {

    // private variables
    private int id;
    private String firstName;
    private String surname;
    private String phoneNumber;
    private String email;
    private String bloodGroup;
    private String urgencyNumber;
    private String drName;
    private String drNumber;

    public User() {
    }

    /**
     *     constructor
     */
    public User(int id, String firstName, String surname, String phoneNumber, String email,
                String bloodGroup, String urgencyNumber, String drName, String drNumber) {
        this.id = id;
        this.firstName = firstName;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.bloodGroup = bloodGroup;
        this.urgencyNumber = urgencyNumber;
        this.drName = drName;
        this.drNumber = drNumber;

    }

    // constructor
    public User(String firstName, String surname, String phoneNumber, String email,
                String bloodGroup, String urgencyNumber, String drName, String drNumber) {
        this.firstName = firstName;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.bloodGroup = bloodGroup;
        this.urgencyNumber = urgencyNumber;
        this.drName = drName;
        this.drNumber = drNumber;
    }

    // getting ID
    public int getID() {
        return this.id;
    }

    // setting id
    public void setID(int id) {
        this.id = id;
    }

    // getting firstName
    public String getFirstName() {
        return this.firstName;
    }

    // setting firstName
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // getting surname
    public String getSurname() {
        return this.surname;
    }

    // setting surname
    public void setSurname(String surname) {
        this.surname = surname;
    }

    // getting phone number
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    // setting phone number
    public void setPhoneNumber(String phone_number) {
        this.phoneNumber = phone_number;
    }

    // getting email
    public String getEmail() {
        return this.email;
    }

    // setting email
    public void setEmail(String email) {
        this.email = email;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public String getUrgencyNumber() {
        return urgencyNumber;
    }

    public String getDrName() {
        return drName;
    }

    public String getDrNumber() {
        return drNumber;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public void setUrgencyNumber(String urgencyNumber) {
        this.urgencyNumber = urgencyNumber;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public void setDrNumber(String drNumber) {
        this.drNumber = drNumber;
    }

}