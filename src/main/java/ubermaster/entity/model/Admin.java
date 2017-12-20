package ubermaster.entity.model;

import ubermaster.annotation.ObjectType;
import ubermaster.entity.attr.AdminAttr;

import java.util.HashMap;

@ObjectType(AdminAttr.OBJTYPE)
public class Admin extends User {
    public interface Model extends AdminAttr {
    }

    public HashMap getAllFields() {
        return null;
    }

    @Override
    public String toString() {
        return "Admin{"
                + super.toString()
                + "}";
    }
}
