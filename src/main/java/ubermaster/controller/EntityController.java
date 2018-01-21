package ubermaster.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;
import ubermaster.entity.model.*;
import ubermaster.entity.security.JwtAuthenticationRequest;
import ubermaster.entityGenerator.entity.EntityGenerator;
import ubermaster.errorHandler.ErrorHandler;
import ubermaster.errorHandler.Errors;
import ubermaster.persistence.facade.Facade;
import ubermaster.persistence.manager.Manager;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
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
                                  String entityClass)
			throws SQLException, ParserConfigurationException, SAXException, IOException {
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
			throws SQLException, ParserConfigurationException, SAXException, IOException
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
			throws SQLException, ParserConfigurationException, SAXException, IOException, ClassNotFoundException
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

           //throw new ClassNotFoundException(exc.getMessage());
		   throw ErrorHandler.createClassNotFoundException(Errors.ser_5);
       }
    }

    @RequestMapping
    (
        value = "/getPokeOrders",
        method = RequestMethod.GET,
        produces = "application/json"
    )
    public BaseEntity[] getPokeOrders(@RequestParam("id") long id)
			throws SQLException, ParserConfigurationException, SAXException, IOException
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
			throws SQLException, ParserConfigurationException, SAXException, IOException
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
    ) throws SQLException, ParserConfigurationException, SAXException, IOException
    {
        return facade.getOrdersByProfession(profession);
    }

    @RequestMapping(value = "/addEntity",
            method = RequestMethod.POST,
            produces = "application/json")
    public void addUser(@RequestBody T entity)
			throws SQLException, ParserConfigurationException, SAXException, IOException{
        facade.createEntity(entity);
    }

    @RequestMapping(value = "/deleteEntity",
            method = RequestMethod.DELETE)
    public void deleteUser(
            @RequestParam("id") long id)
			throws SQLException, ParserConfigurationException, SAXException, IOException{
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
    ) throws SQLException, ParserConfigurationException, SAXException, IOException
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
	) throws SQLException, ParserConfigurationException, SAXException, IOException
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
    ) throws SQLException, ParserConfigurationException, SAXException, IOException
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
	) throws SQLException, ParserConfigurationException, SAXException, IOException
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
	) throws SQLException, ParserConfigurationException, SAXException, IOException
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
	) throws SQLException, ParserConfigurationException, SAXException, IOException
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
	) throws SQLException, ParserConfigurationException, SAXException, IOException
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
	) throws SQLException, ParserConfigurationException, SAXException, IOException
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
	) throws SQLException, ParserConfigurationException, SAXException, IOException
	{
		facade.createEntity(order);

		return "DONE";
	}

	@RequestMapping
	(
		value = "/masterDoneOrder",
		method = RequestMethod.GET,
		produces = "application/json"
	)
	public String masterDoneOrder(@RequestParam("id") long orderID)
	{
		facade.setOrderStatus(orderID, -2, "Master done");

		return "DONE";
	}

	@RequestMapping
	(
		value = "/masterCancelDoneOrder",
		method = RequestMethod.GET,
		produces = "application/json"
	)
	public String masterCancelDoneOrder(@RequestParam("id") long orderID)
	{
		facade.setOrderStatus(orderID, -2, "In progress");

		return "DONE";
	}

	@RequestMapping
	(
		value = "/masterAcceptOrder",
		method = RequestMethod.GET,
		produces = "application/json"
	)
	public String masterAcceptOrder
	(
		@RequestParam("id") long orderID,
		@RequestParam("mid") long masterID
	)
	{
		facade.setOrderStatus(orderID, masterID, "In progress");

		return "DONE";
	}

	@RequestMapping
	(
		value = "/masterDiscardOrder",
		method = RequestMethod.GET,
		produces = "application/json"
	)
	public String masterDiscardOrder(@RequestParam("id") long orderID)
	{
		facade.setOrderStatus(orderID, -1, "New");

		return "DONE";
	}

	@RequestMapping
	(
		value = "/getOrdersByStatus",
		method = RequestMethod.POST,
		produces = "application/json"
	)
	public BaseEntity[] getOrdersByStatus
	(
		@RequestParam("status") String status
	)
	{
		return facade.getOrdersByStatus(status);
	}

}
