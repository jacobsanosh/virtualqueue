package org.gptccherthala.virtualqueue.user;

public class UserDatabase {
    public String name;
    public long phoneNumber;
    public int pinCode;

    public String getName() {
        return name;
    }

    public void setName(String Name) {
        this.name = Name;
    }

    public UserDatabase() {
        System.out.println("in empty cons");
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getPinCode() {
        return pinCode;
    }

    public void setPinCode(int pinCode) {
        this.pinCode = pinCode;
    }

    public UserDatabase(long phoneNumber, String Name, int pinCode) {
        this.name = Name;
        this.phoneNumber = phoneNumber;
        this.pinCode = pinCode;
    }
}
