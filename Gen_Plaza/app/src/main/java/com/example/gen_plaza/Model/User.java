package com.example.gen_plaza.Model;

public class User {


    private String avatar;
    private String f_name;
    private String l_name;

    public User(String Image, String fName, String Lname ) {
        avatar = Image;
        f_name  = fName;
        l_name = Lname;
    }

    public String getImageUrl() {
        return avatar;
    }

    public String getFirstName(){
        return f_name;
    }

    public String getLastName(){
        return l_name;
    }
}
