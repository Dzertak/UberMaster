package entity.model;

import annotation.ObjectType;
import entity.attr.PokeAttr;

@ObjectType(PokeAttr.OBJTYPE)
public class Poke extends User {
    public interface Model extends PokeAttr{

    }

    @Override
    public String toString() {
        return "Poke{}";
    }
}
