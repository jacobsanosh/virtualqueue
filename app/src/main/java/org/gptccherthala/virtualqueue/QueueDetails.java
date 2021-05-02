package org.gptccherthala.virtualqueue;

import java.sql.Time;

public class QueueDetails {
    private String name;
    private String uname;
    private long position;
    private String uId;
    private Time time;
    private  long phone;
    private int pincode;

    public QueueDetails(String uname,long phone,int pincode,long position) {
        this.uname = uname;
        this.position = position;
        this.phone = phone;
        this.pincode=pincode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBusinessName() {
        return uname;
    }

    public void setBusinessName(String businessName) {
        this.uname = uname;
    }

    public long getPosition() {
        return position;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}