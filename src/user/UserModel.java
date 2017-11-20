package user;

import annotation.AttrType;
import annotation.ObjectType;

@ObjectType(UserAttr.OBJTYPE)
public class UserModel {

    @AttrType(UserAttr.LOCATION)
    protected String location;

    @AttrType(UserAttr.DESCRIPTION)
    protected String description;

    @AttrType(UserAttr.PHONE_NUMBER)
    protected String phoneNumber;

    @AttrType(UserAttr.PASSWORD)
    protected String password;

    @AttrType(UserAttr.PICTURE)
    protected String picture;

}
