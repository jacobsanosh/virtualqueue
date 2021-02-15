package org.gptccherthala.virtualqueue;

public class Users {
    public  String UserName;
    public long phno,pincode;

    public Users() {

    }

    public Users(String userName, long phno, long pincode) {
        this.UserName = userName;
        this.phno = phno;
        this.pincode = pincode;
    }
}
