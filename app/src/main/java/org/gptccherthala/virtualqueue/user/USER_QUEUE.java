package org.gptccherthala.virtualqueue.user;

public class USER_QUEUE {
    String Uid;
    String name;
    long phone;
    long qlength;
    int pincode;

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public USER_QUEUE() {
    }

    public USER_QUEUE(String name, long phone, long qlength, int pincode) {
        this.name = name;
        this.phone = phone;
        this.qlength = qlength;
        this.pincode = pincode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public long getQlength() {
        return qlength;
    }

    public void setQlength(long qlength) {
        this.qlength = qlength;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }
}
