package com.example.addd.myapplication.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * The Objectify object model for device registrations we are persisting
 */
@Entity
public class RegistrationRecord {

    @Id
    Long id;

    @Index
    private String regId, nama;



    // you can add more fields...

    public RegistrationRecord() {
    }


    public String getRegId() {
        return regId;
    }



    public void setRegId(String regId) {
        this.regId = regId;
    }
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}