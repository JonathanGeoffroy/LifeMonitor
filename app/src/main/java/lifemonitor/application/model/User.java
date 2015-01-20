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

    public User() {
    }

    /**
     *     constructor
     */
    public User(int id, String firstName, String surname, String phoneNumber, String _email) {
        this.id = id;
        this.firstName = firstName;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.email = _email;

    }

    // constructor
    public User(String firstName, String surname, String phoneNumber, String _email) {
        this.firstName = firstName;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.email = _email;
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

}