package com.dota.atmos.atmos2k16.Set;

/**
 * Created by SHREEDA on 24-09-2016.
 */

public class Contacts {
    int image;
    String name,designation,mobile,email;

    public Contacts(String name, String designation, String mobile, String email,int image) {
        this.name = name;
        this.image=image;
        this.designation = designation;
        this.mobile = mobile;
        this.email = email;

    }

    public int getImage()
    {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getDesignation() {
        return designation;
    }

    public String getMobile() {
        return mobile; }

    public String getEmail() {
        return email;
    }
}
