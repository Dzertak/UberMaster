package ubermaster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ubermaster.entity.model.BaseEntity;
import ubermaster.entity.model.Poke;
import ubermaster.model.StackoverflowWebsite;
import ubermaster.persistence.facade.Facade;
import ubermaster.service.StackoverflowService;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/entities")
public class EntityController<T extends BaseEntity> {

    @Autowired
    private Facade facade;

    @Autowired
    private StackoverflowService stackoverflowService = new StackoverflowService();

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    List<T> getAllUsers() {
        return null;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<StackoverflowWebsite> getListofProviders() throws SQLException {
        return stackoverflowService.findAll();
    }

    @RequestMapping(value = "/getEntity", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    T getUsersById(@RequestParam("id") long id, @RequestParam("CLASS") String CLASS) {
        return facade.getEntity(id, Poke.class);
    }

    @RequestMapping(value = "/addEntity", method = RequestMethod.POST, produces = "application/json")
    public void addUser(@RequestBody T entity) {
        facade.createEntity(entity);
    }

    @RequestMapping(value = "/deleteEntity", method = RequestMethod.DELETE)
    public @ResponseBody
    T deleteUser(@RequestParam("id") long id) {
        return null;
    }
}
