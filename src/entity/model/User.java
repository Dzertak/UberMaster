package entity.model;

import annotation.Attribute;
import annotation.ObjectType;
import entity.attr.UserAttr;

import java.lang.reflect.Field;
import java.util.HashMap;

@ObjectType(UserAttr.OBJTYPE)
public class User extends BaseEntity
{

    public interface Model extends UserAttr{

    }
    @Attribute(Model.LOCATION)
    protected String location;

    @Attribute(Model.DESCRIPTION)
    protected String userDescription;

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

    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String description) {
        this.userDescription = description;
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

    public void fillAttributeFields(HashMap<String, Object> hashMap)
    {
        Field sqcField[] = User.class.getDeclaredFields();
        Attribute attrib;
        int length = sqcField.length;
        try
        {
            for (int i = 0; i < length; ++i)
            {
                attrib = sqcField[i].getAnnotation(Attribute.class);

                if (attrib != null)
                    sqcField[i].set(this, hashMap.get(attrib.value()));
            }
        }

        catch (IllegalAccessException exc)
        {
            exc.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "User{"
                + super.toString()
                + "\nlocation='" + location + '\'' +
                ", \ndescription='" + userDescription + '\'' +
                ", \nphoneNumber='" + phoneNumber + '\'' +
                ", \npassword='" + password + '\'' +
                ", \npicture='" + picture + '\'' +
                '}';
    }
}
