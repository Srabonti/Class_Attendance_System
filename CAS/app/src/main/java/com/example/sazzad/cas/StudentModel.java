package com.example.sazzad.cas;

/**
 * Created by Sazzad on 7/9/2017.
 */

public class StudentModel {
    String id, name, guardianContact;

    public StudentModel(String id, String name, String guardianContact) {
        this.id = id;
        this.name = name;
        this.guardianContact = guardianContact;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGuardianContact() {
        return guardianContact;
    }

    public void setGuardianContact(String guardianContact) {
        this.guardianContact = guardianContact;
    }
}
