package entity.model;

import annotation.AttrType;
import annotation.ObjectType;
import entity.attr.UserAttr;

@ObjectType(UserAttr.OBJTYPE)
public class User {

    public interface Model extends UserAttr{

    }
    @AttrType(Model.LOCATION)
    protected String location;

    @AttrType(Model.DESCRIPTION)
    protected String description;

    @AttrType(Model.PHONE_NUMBER)
    protected String phoneNumber;

    @AttrType(Model.PASSWORD)
    protected String password;

    @AttrType(Model.PICTURE)
    protected String picture;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "User{" +
                "location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }
}
