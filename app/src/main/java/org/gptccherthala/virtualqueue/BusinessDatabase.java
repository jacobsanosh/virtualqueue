package org.gptccherthala.virtualqueue;

public class BusinessDatabase {
    public String name;
    public String address;
    public long phone;
    public int pincode;
    public String description;
    public String category;
    public int image;
    public String imageUrl;

    public BusinessDatabase(){}

    public BusinessDatabase(int image) {
        this.image = image;
    }

    public BusinessDatabase(String name, String address, long phone, int pincode, String description, String category, String imageUrl) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.pincode = pincode;
        this.description = description;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public int getImage() {
        return image;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
