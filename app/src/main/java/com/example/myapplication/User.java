package com.example.myapplication;

public class User {
    private String username;
    private String password;
    private String email;
    private String fullName;

    public User(){
    }

    public User(String email, String fullName, String password, String username){
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }
    public String getFullName() {
        return fullName;
    }
    public String getPassword() {
        return password;
    }
    public String getUsername(){
        return username;
    }
}
