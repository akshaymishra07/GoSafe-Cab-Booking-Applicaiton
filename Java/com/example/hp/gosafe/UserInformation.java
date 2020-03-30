package com.example.hp.gosafe;

public class UserInformation {

    String username;

    String email;
    String password;

    public UserInformation() {
    }

    public UserInformation(String username , String email, String password) {
        this.username = username;

        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }



    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
