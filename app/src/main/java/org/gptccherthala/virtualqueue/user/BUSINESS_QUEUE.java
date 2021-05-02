package org.gptccherthala.virtualqueue.user;

public class BUSINESS_QUEUE {
    String businessname;
    long position;
    long  phone;

    public BUSINESS_QUEUE() {
    }

    public BUSINESS_QUEUE(String businessname, long position,long phone) {
        this.businessname = businessname;
        this.position = position;
        this.phone = phone;
    }

    public String getBusinessname() {
        return businessname;
    }

    public void setBusinessname(String businessname) {
        this.businessname = businessname;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }
}
