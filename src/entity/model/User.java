package entity.model;

import annotation.Attribute;
import annotation.ObjectType;
import entity.attr.UserAttr;

@ObjectType(UserAttr.OBJTYPE)
public class User extends BaseEntity
{

    public interface Model extends UserAttr{

    }
    @Attribute(Model.LOCATION)
    protected String location;

    @Attribute(Model.DESCRIPTION)
    protected String description;

    @Attribute(Model.PHONE_NUMBER)
    protected String phoneNumber;

    @Attribute(Model.PASSWORD)
    protected String password;

    @Attribute(Model.PICTURE)
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
        return "User{"
                + super.toString()
                + "\nlocation='" + location + '\'' +
                ", \ndescription='" + description + '\'' +
                ", \nphoneNumber='" + phoneNumber + '\'' +
                ", \npassword='" + password + '\'' +
                ", \npicture='" + picture + '\'' +
                '}';
    }
}
