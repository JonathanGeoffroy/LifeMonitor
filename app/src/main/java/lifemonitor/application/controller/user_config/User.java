package lifemonitor.application.controller.user_config;

public class User {

    // private variables
    private int _id;
    private String _fname;
    private String _sname;
    private String _phone_number;
    private String _email;

    public User() {
    }

    // constructor
    public User(int id, String fname, String sname, String _phone_number, String _email) {
        this._id = id;
        this._fname = fname;
        this._sname = sname;
        this._phone_number = _phone_number;
        this._email = _email;

    }

    // constructor
    public User(String fname, String sname, String _phone_number, String _email) {
        this._fname = fname;
        this._sname = sname;
        this._phone_number = _phone_number;
        this._email = _email;
    }

    // getting ID
    public int getID() {
        return this._id;
    }

    // setting id
    public void setID(int id) {
        this._id = id;
    }

    // getting fname
    public String getFirstName() {
        return this._fname;
    }

    // setting fname
    public void setFirstName(String fname) {
        this._fname = fname;
    }

    // getting sname
    public String getSurname() {
        return this._sname;
    }

    // setting sname
    public void setSurname(String sname) {
        this._sname = sname;
    }

    // getting phone number
    public String getPhoneNumber() {
        return this._phone_number;
    }

    // setting phone number
    public void setPhoneNumber(String phone_number) {
        this._phone_number = phone_number;
    }

    // getting email
    public String getEmail() {
        return this._email;
    }

    // setting email
    public void setEmail(String email) {
        this._email = email;
    }

}