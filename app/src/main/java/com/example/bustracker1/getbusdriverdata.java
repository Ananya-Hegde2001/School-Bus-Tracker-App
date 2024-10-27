package com.example.bustracker1;

public class getbusdriverdata {
    String Name,Email,Password,Message;

    getbusdriverdata(){

    }

    public String getName() {
        return Name;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    getbusdriverdata(String name, String email, String pswd,String message){
        this.Name = name;
        this.Email = email;
        this.Password = pswd;
        this.Message = message;
    }
}
