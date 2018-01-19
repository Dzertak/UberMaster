package ubermaster.entity.model;

import ubermaster.annotation.ObjectType;
import ubermaster.entity.attr.PokeAttr;

import java.util.HashMap;

@ObjectType(PokeAttr.OBJTYPE)
public class Poke extends BlockedUser {
    public interface Model extends PokeAttr {
    }

    public Poke()
    {
       super("Poke");
    }

    @Override
    public HashMap getAllFields() {
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
