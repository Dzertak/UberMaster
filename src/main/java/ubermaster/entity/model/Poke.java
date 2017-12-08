package ubermaster.entity.model;

import ubermaster.annotation.ObjectType;
import ubermaster.entity.attr.PokeAttr;

import java.util.HashMap;

@ObjectType(PokeAttr.OBJTYPE)
public class Poke extends User
{
/*::|		FIELD		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
/*::|		CONSTRUCTOR		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
/*::|		SUB_CLASS		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
    public interface Model extends PokeAttr
    {                       }
/*::|		F / P		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
    @Override
    public HashMap getAllFields()
    {
        HashMap<String, Object> hashmap = super.getAllFields();

        return hashmap;
    }

    @Override
    public String toString()
    {
        return "Poke{"
                + super.toString()
                + "}";
    }
}
