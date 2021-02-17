package org.gptccherthala.virtualqueue;

public class BusinessDatabase {
    public String name;
    public String address;
    public long phone;
    public int pincode;
    public String description;
    public String category;

    public BusinessDatabase(){}

    public BusinessDatabase(String name, String address, long phone, int pincode, String description, String category) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.pincode = pincode;
        this.description = description;
        this.category = category;
    }
}
