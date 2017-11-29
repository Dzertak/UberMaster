package entity.model;

import annotation.Attribute;
import annotation.ObjectType;
import entity.attr.AdminAttr;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.HashMap;

@ObjectType(AdminAttr.OBJTYPE)
public class Admin extends User {

    public interface Model extends AdminAttr{

    }

    public void fillAttributeFields(HashMap<String, Object> hashMap)
    {
        super.fillAttributeFields(hashMap);

        Field sqcField[] = Admin.class.getDeclaredFields();
        Attribute attrib;
        int length = sqcField.length;
        try
        {
            for (int i = 0; i < length; ++i)
            {
                attrib = sqcField[i].getAnnotation(Attribute.class);

                if (attrib != null)
                    setField(sqcField[i], (String)hashMap.get(attrib.value()), this);
            }
        }

        catch (IllegalAccessException | ParseException exc)
        {
            exc.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Admin{"
                + super.toString()
                + "}";
    }
}
