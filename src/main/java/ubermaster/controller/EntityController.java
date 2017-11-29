package ubermaster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import ubermaster.entity.model.BaseEntity;
import ubermaster.persistence.facade.Facade;

import java.util.List;

@Controller
@RequestMapping("/users")
public class EntityController<T extends BaseEntity> {

    @Autowired
    private Facade facade;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    List<T> getAllUsers() {
        return null;
    }

    @RequestMapping(value = "/getEntity", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    T getUsersById(@RequestParam("id") long id, @RequestParam("CLASS") String CLASS) {
        try {
            return facade.getEntity(id, (Class<? extends BaseEntity>) Class.forName(CLASS));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/addEntity", method = RequestMethod.POST, produces = "application/json")
    public void addUser(@RequestBody T entity) {
        facade.createEntity(entity, false);
    }

    @RequestMapping(value = "/addListOfUsers", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    List<T> addUsers(@RequestBody List<T> entities) {
        return null;
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.DELETE)
    public @ResponseBody
    T deleteUser(@RequestParam("id") long id) {
        return null;
    }
}
