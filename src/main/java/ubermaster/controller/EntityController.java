package ubermaster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import scriptsInitializer.ScriptInitializer;
import ubermaster.entity.model.*;
import entityGenerator.entity.EntityGenerator;
import ubermaster.persistence.facade.Facade;

import java.util.Random;

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

    @RequestMapping
    (
        value = "/getUserByPhonePass",
        method = RequestMethod.GET,
        produces = "application/json"
    )
    public User getUsersByPhoneAndPass
    (
        @RequestParam("phone") String phoneNumber,
        @RequestParam("password") String password
    )
    {
        return facade.getUser(phoneNumber, password);
    }

    @RequestMapping
    (
        value = "/getUserByPhone",
        method = RequestMethod.GET,
        produces = "application/json"
    )
    public User getUsersByPhone(@RequestParam("phone") String phoneNumber)
    {
        return facade.getUserByPhone(phoneNumber);
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

    @RequestMapping
    (
        value = "/getOrdersByProfession",
        method = RequestMethod.GET,
        produces = "application/json"
    )
    public BaseEntity[] getOrdersByProfession
    (
        @RequestParam("profession") String profession
    )   throws ClassNotFoundException
    {
        return facade.getOrdersByProfession(profession);
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

    /**
     * Method is used for creating gene entities
     * */
    @RequestMapping(value = "/generate")
    public String generateEntities()
    {
        EntityGenerator generator = new EntityGenerator
                (
                    new Random(0),
                    0.5f,
                    20,
                    10,
                    10
                );
        generator.init();

        return "DONE";
    }

    /**
     * Method is used for creating DB scripts
     * */
    @RequestMapping(value = "/dbscripts")
    public String databaseScripts()
    {
        ScriptInitializer scriptInitializer = new ScriptInitializer();
        scriptInitializer.init();
        return "DONE";
    }
}
