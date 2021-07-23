package com.example.sazzad.cas;

/**
 * Created by Sazzad on 7/9/2017.
 */

public class UserModel {
    String adminName,adminEmail, adminContact;

    public UserModel(String adminName, String adminEmail, String adminContact) {
        this.adminName = adminName;
        this.adminEmail = adminEmail;
        this.adminContact = adminContact;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getAdminContact() {
        return adminContact;
    }

    public void setAdminContact(String adminContact) {
        this.adminContact = adminContact;
    }
}
