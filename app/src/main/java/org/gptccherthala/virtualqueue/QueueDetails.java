package org.gptccherthala.virtualqueue;

import java.sql.Time;

public class QueueDetails {
    private String name;
    private String businessName;
    private long position;
    private String uId;
    private Time time;

    public QueueDetails(String businessName, long position) {
        this.businessName = businessName;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
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