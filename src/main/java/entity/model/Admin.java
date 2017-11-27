package entity.model;

import annotation.ObjectType;
import entity.attr.AdminAttr;

@ObjectType(AdminAttr.OBJTYPE)
public class Admin extends User {

    public interface Model extends AdminAttr{

    }

    @Override
    public String toString() {
        return "Admin{}";
    }
}
