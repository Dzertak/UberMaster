package ubermaster.persistence.manager;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.xml.sax.SAXException;
import ubermaster.entity.model.BaseEntity;
import ubermaster.entity.model.PersistenceEntity;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;

public interface Manager
{
/*::|       FIELD       :~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
    String GET_ENTITY = "{call getEntity(?, ?)}";
    String GET_TYPED_ENTITIES = "{call getTypedEntities(?, ?)}";
    String GET_POKE_ORDERS = "{call getPokeOrders(?, ?)}";
    String GET_MASTER_ORDERS = "{call getMasterOrders(?, ?)}";
    String GET_USER = "{call getUser(?, ?, ?)}";
    String GET_USER_BY_PHONE = "{call getUserByPhone(?, ?)}";
    String DELETE_ENTITY = "delete from Objects where object_id = ?";
    String INSERT_ENTITY = "{call insertEntity(?)}";
    String UPDATE_ENTITY = "{call updateEntity(?)}";
    String GET_ORDER_BY_PROFESSION = "call getOrdersByProfession(?, ?)";

    String ATTR_OBJECT_ID = "-1";
    String ATTR_OBJECT_TYPE_ID = "-2";
    String ATTR_NAME = "-3";
    String ATTR_DESCR = "-4";

    String ARRAY_ENTITIES = "ARRAYENTITES";

    byte MASTER_TYPE_ORDERS = 1;
    byte POKE_TYPE_ORDERS = 2;
/*::|       SUB_CLASS       :~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
/*::|       F / P       :~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
    /**
     * Inserts an instance of {@code BaseEntity} child class into data base
     *
     * @param persistenceEntity — an entity
     *
     * @param _class — a type of instance
     * */
    void createEntity(PersistenceEntity persistenceEntity, Class<? extends BaseEntity> _class)
            throws SQLException, ParserConfigurationException, SAXException, IOException;

    /**
     * Get an entity from data base by entity id
     *
     * @param id — entity id
     *
     * @param _class — a class type of entity
     *
     * @return an instance of {@code PersistenceEntity} class
     * */
    PersistenceEntity getEntity(long id, Class<? extends BaseEntity> _class)
            throws SQLException, ParserConfigurationException, SAXException, IOException;

    /**
     * Get an array of entities from data base by class type
     *
     * @param _class — a class type of entity
     *
     * @return an instance of array of {@code PersistenceEntity} instances
     * */
    PersistenceEntity[] getTypedEntities(Class<? extends BaseEntity> _class)
            throws SQLException, ParserConfigurationException, SAXException, IOException;

    /**
     * Get a user from data base by phone number
     *
     * @param phoneNumber — a phone number of entity
     *
     * @return an instance of {@code PersistenceEntity} instance
     * */
    PersistenceEntity getUserByPhone(String phoneNumber)
        throws UsernameNotFoundException, ParserConfigurationException, SAXException, IOException;

    /**
     * Get a user from data base by phone number and password
     *
     * @param phoneNumber — a phone number of entity
     * @param password  — a password of entity
     *
     * @return an instance of {@code PersistenceEntity} instance
     * */
    PersistenceEntity getUserByPhonePass(String phoneNumber, String password)
            throws SQLException, ParserConfigurationException, SAXException, IOException;

    /**
     * Get the orders of master or poke in array of {@code PersistenceEntity}
     * instances
     *
     * @param id — an id of entity
     * @param userType — int value of class type from Manager
     *
     * @return an instance of array of {@code PersistenceEntity} instances
     * */
    PersistenceEntity[] getUserOrders(long id, int userType)
            throws SQLException, ParserConfigurationException, SAXException, IOException;

    /**
     * Get the orders in array of {@code PersistenceEntity} by profession
     * instances
     *
     * @param profession —  order profession
     *
     * @return an instance of array of {@code PersistenceEntity} instances
     * */
    PersistenceEntity[] getOrdersByProfession(String profession)
            throws SQLException, ParserConfigurationException, SAXException, IOException;

    /**
     * Deletes entity from data base by entity id
     *
     * @param id — entity id
     * */
    void deleteEntity(long id)
            throws SQLException, ParserConfigurationException, SAXException, IOException;

    /**
     * Method is used for updating entities
     *
     * @param id — id of entity
     * @param sqcParam — an array of object couples. First couple element is attr_id
     *      of attribute and second one is value
     * */
    void updateEntity(long id, Object ... sqcParam)
            throws SQLException, ParserConfigurationException, SAXException, IOException;
}
