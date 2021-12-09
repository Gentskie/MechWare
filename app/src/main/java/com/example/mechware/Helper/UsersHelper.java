package com.example.mechware.Helper;

public class UsersHelper {

    public String fullname, email, contact_number, password;

    public UsersHelper() {
    }

    public UsersHelper(String fullname, String email, String contact_number, String password) {
        this.fullname = fullname;
        this.email = email;
        this.contact_number = contact_number;
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }

    public String getContact_number() {
        return contact_number;
    }

    public String getPassword() {
        return password;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
