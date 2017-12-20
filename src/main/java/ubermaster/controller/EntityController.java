package ubermaster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ubermaster.entity.model.BaseEntity;
import ubermaster.entity.model.Poke;
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
    public T getUsersById(@RequestParam("id") long id,
                          @RequestParam("CLASS") String entityClass) {
        return facade.getEntity(id, Poke.class);
    }

    @RequestMapping(value = "/addEntity",
            method = RequestMethod.POST,
            produces = "application/json")
    public void addUser(@RequestBody T entity) {
        facade.createEntity(entity);
    }

    @RequestMapping(value = "/deleteEntity",
            method = RequestMethod.DELETE)
    public T deleteUser(@RequestParam("id") long id) {
        return null;
    }
}
