package ubermaster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ubermaster.entity.model.*;
import ubermaster.entityGenerator.entity.EntityGenerator;
import ubermaster.persistence.facade.Facade;

@RestController
@RequestMapping("/entities")
public class EntityController<T extends BaseEntity>
{
    @Autowired
    private Facade facade;

    @RequestMapping(value = "/getEntity",
            method = RequestMethod.GET,
            produces = "application/json")
    public T getUsersById(@RequestParam("id")
                                  long id,
                          @RequestParam("class")
                                  String entityClass) {
        if (entityClass.equals(Poke.class.getSimpleName())) {
            return facade.getEntity(id, Poke.class);
        }
        if (entityClass.equals(Master.class.getSimpleName())) {
            return facade.getEntity(id, Master.class);
        }
        if (entityClass.equals(Order.class.getSimpleName())) {
            return facade.getEntity(id, Order.class);
        }
        return null;
    }

    @RequestMapping(value = "/getUser",
            method = RequestMethod.GET,
            produces = "application/json")
    public User getUsersByPhone(@RequestParam("phone")
                                        String phoneNumber,
                                @RequestParam("password")
                                        String password)
    {
        return facade.getUser(phoneNumber, password);
    }

    @RequestMapping(value = "/getTypedEntity",
            method = RequestMethod.GET,
            produces = "application/json")
    public BaseEntity[] getTypedEntity(@RequestParam("class") String type) throws ClassNotFoundException
    {
        Class<? extends BaseEntity> _class = (Class<? extends BaseEntity>)
                            Class.forName("ubermaster.entity.model." + type);
        return facade.getTypedEntities(_class);
    }

    @RequestMapping(value = "/getPokeOrders",
            method = RequestMethod.GET,
            produces = "application/json")
    public BaseEntity[] getPokeOrders(@RequestParam("id") long id) throws ClassNotFoundException
    {
        return facade.getPokeOrders(id);
    }

    @RequestMapping(value = "/addEntity",
            method = RequestMethod.POST,
            produces = "application/json")
    public void addUser(@RequestBody T entity) {
        facade.createEntity(entity);
    }

    @RequestMapping(value = "/deleteEntity",
            method = RequestMethod.DELETE)
    public void deleteUser(
            @RequestParam("id") long id) {
        facade.deleteEntity(id);
    }

    @RequestMapping(value = "/generate")
    public String generateEntities()
    {
        EntityGenerator.init();

        return "DONE";
    }
}
