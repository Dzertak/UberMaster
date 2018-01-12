package ubermaster.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ubermaster.entity.model.*;
import ubermaster.entity.security.JwtAuthenticationRequest;
import ubermaster.entityGenerator.entity.EntityGenerator;
import ubermaster.persistence.facade.Facade;
import ubermaster.persistence.manager.Manager;

import java.sql.SQLException;
import java.util.Date;
import java.util.Random;

@RestController
@RequestMapping("/entities")
public class EntityController<T extends BaseEntity>
{
    private static final Logger log = Logger.getLogger(EntityController.class);

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
        value = "/getUserByPhone",
        method = RequestMethod.GET,
        produces = "application/json"
    )
    public User getUsersByPhone(@RequestParam("phone") String phoneNumber)
    {
        return facade.getUserByPhone(phoneNumber);
    }

    @RequestMapping
    (
        value = "/getTypedEntities",
        method = RequestMethod.GET,
        produces = "application/json"
    )
    public BaseEntity[] getTypedEntity(@RequestParam("class") String type)
        throws ClassNotFoundException
    {
       try
       {
           Class<? extends BaseEntity> _class = (Class<? extends BaseEntity>)
           Class.forName("ubermaster.entity.model." + type);
           return facade.getTypedEntities(_class);
       }

       catch(ClassNotFoundException exc)
       {
           log.error(exc.getMessage(),exc);

           throw new ClassNotFoundException(exc.getMessage());
       }
    }

    @RequestMapping
    (
        value = "/getPokeOrders",
        method = RequestMethod.GET,
        produces = "application/json"
    )
    public BaseEntity[] getPokeOrders(@RequestParam("id") long id)
    {
        return facade.getUserOrders(id, Manager.POKE_TYPE_ORDERS);
    }

    @RequestMapping
    (
        value = "/getMasterOrders",
        method = RequestMethod.GET,
        produces = "application/json"
    )
    public BaseEntity[] getMasterOrders(@RequestParam("id") long id)
    {
        return facade.getUserOrders(id, Manager.MASTER_TYPE_ORDERS);
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
    )
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

    @RequestMapping
    (
        value = "/setUserBlock",
        method = RequestMethod.POST,
        produces = "application/json"
    )
    public String setUserBlock
    (
        @RequestParam("id") long id,
        @RequestParam("isBlocked") boolean isBlocked
    )
    {
        facade.setBlocked(id, isBlocked);

        return "DONE";
    }

	@RequestMapping
	(
		value = "/setOrderStatus",
		method = RequestMethod.POST,
		produces = "application/json"
	)
	public String setOrderStatus
	(
		@RequestParam("id") long id,
		@RequestParam("status") String status,
		@RequestParam("mid") long mid
	)
	{
		facade.setOrderStatus(id, mid, status);

		return "DONE";
	}

    @RequestMapping
    (
        value = "/setUserPicture",
        method = RequestMethod.POST,
        produces = "application/json"
    )
    public String setUserPicture
    (
        @RequestParam("id") long id,
        @RequestParam(value = "picture") String pictureURL
    )
    {
        facade.setUserPicture(id, pictureURL);

        return "DONE";
    }

    @RequestMapping
	(
		value = "/updatePoke",
		method = RequestMethod.POST,
		produces = "application/json"
	)
	public String updatePoke
	(
		@RequestBody Poke poke
	)
	{
		facade.updateEntity(poke);

		return "DONE";
	}

	@RequestMapping
	(
		value = "/updateMaster",
		method = RequestMethod.POST,
		produces = "application/json"
	)
	public String updateMaster
	(
		@RequestBody Master master
	)
	{
		facade.updateEntity(master);

		return "DONE";
	}

	@RequestMapping
	(
		value = "/updateOrder",
		method = RequestMethod.POST,
		produces = "application/json"
	)
	public String updateOrder
	(
		@RequestBody Order order
	)
	{
		facade.updateEntity(order);

		return "DONE";
	}

	@RequestMapping
	(
		value = "/addPoke",
		method = RequestMethod.POST,
		produces = "application/json"
	)
	public String addPoke
	(
		@RequestBody Poke poke
	)
	{
		facade.createEntity(poke);

		return "DONE";
	}

	@RequestMapping
	(
		value = "/addMaster",
		method = RequestMethod.POST,
		produces = "application/json"
	)
	public String addMaster
	(
		@RequestBody Master master
	)
	{
		facade.createEntity(master);

		return "DONE";
	}

	@RequestMapping
	(
		value = "/addOrder",
		method = RequestMethod.POST,
		produces = "application/json"
	)
	public String addOrder
	(
		@RequestBody Order order
	)
	{
		facade.createEntity(order);

		return "DONE";
	}
}
