package entity;

import entity.model.UserModel;

public class User extends UserModel
{
    public String getLocation(){return location;}

    public String getDescription()
    {
        return description;
    }

    public String getPhoneNumber(){return phoneNumber;}

    public String getPassword(){return password;}

    public String getPicture(){return picture;}

}
