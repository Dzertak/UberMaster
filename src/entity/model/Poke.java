package entity.model;

import annotation.Attribute;
import annotation.ObjectType;
import entity.attr.PokeAttr;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.HashMap;

@ObjectType(PokeAttr.OBJTYPE)
public class Poke extends User {
    public interface Model extends PokeAttr{

    }

    public void fillAttributeFields(HashMap<String, Object> hashMap)
    {
        super.fillAttributeFields(hashMap);

        Field sqcField[] = Poke.class.getDeclaredFields();
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
    public HashMap getAllFields()
    {
        HashMap<String, Object> hashmap = super.getAllFields();

        return hashmap;
    }

    @Override
    public String toString() {
        return "Poke{"
                + super.toString()
                + "}";
    }
}
