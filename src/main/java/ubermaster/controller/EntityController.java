package ubermaster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ubermaster.entity.model.*;
import ubermaster.persistence.facade.Facade;

import java.util.List;

@RestController
@RequestMapping("/entities")
public class EntityController<T extends BaseEntity> {

    @Autowired
    private Facade facade;

    @RequestMapping(value = "/",
            method = RequestMethod.GET,
            produces = "application/json")
    public List<T> getAllUsers() {
        return null;
    }

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
                                        String password) {
        return facade.getUser(phoneNumber, password);
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
}
