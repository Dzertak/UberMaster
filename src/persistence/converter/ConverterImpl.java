package persistence.converter;

import entity.model.*;
import persistence.PersistenceEntity;

import java.math.BigInteger;
import java.sql.Date;
import java.util.HashMap;


public class ConverterImpl implements Converter
{
    public PersistenceEntity convertToEntity(BaseEntity baseEntity)
    {
        PersistenceEntity persistenceEntity = new PersistenceEntity();
        persistenceEntity.setObject_id(baseEntity.getObject_id());
        persistenceEntity.setName(baseEntity.getName());
        persistenceEntity.setDescription(baseEntity.getDescription());

        baseEntity.getClass().getFields();

        if (baseEntity instanceof Order)
            convertOrderToEntity((Order)baseEntity, persistenceEntity);

        else if (baseEntity instanceof Master)
            convertMasterToEntity((Master)baseEntity, persistenceEntity);

        else if (baseEntity instanceof Poke)
            convertPokeToEntity((Poke)baseEntity, persistenceEntity);

        else if (baseEntity instanceof User)
            convertUserToEntity((User)baseEntity, persistenceEntity);

        else if (baseEntity instanceof Admin)
            convertAdminToEntity((Admin)baseEntity, persistenceEntity);

        return persistenceEntity;
    }

    private void convertOrderToEntity(Order order, PersistenceEntity persistenceEntity)
    {
        HashMap<BigInteger, Object> orderAttrMap = new HashMap<>();
        orderAttrMap.put(BigInteger.valueOf(0), order.getBigDescription());
        orderAttrMap.put(BigInteger.valueOf(1), order.getDueDate());
        orderAttrMap.put(BigInteger.valueOf(2), order.getSmallDescription());
        orderAttrMap.put(BigInteger.valueOf(3), order.getStartDate());
        orderAttrMap.put(BigInteger.valueOf(4), order.getStatus());
        persistenceEntity.setAttributes(orderAttrMap);

        HashMap<BigInteger, Long> orderRefMap = new HashMap<>();
        //orderRefMap.put(BigInteger.valueOf(0), order.getMaster());
        persistenceEntity.setReferences(orderRefMap);
    }

    private void convertMasterToEntity(Master master, PersistenceEntity persistenceEntity)
    {
        HashMap<BigInteger, Object> masterAttrMap = new HashMap<>();
        masterAttrMap.put(BigInteger.valueOf(0), master.getDescription());
        masterAttrMap.put(BigInteger.valueOf(1), master.getLocation());
        masterAttrMap.put(BigInteger.valueOf(2), master.getPassword());
        masterAttrMap.put(BigInteger.valueOf(3), master.getPhoneNumber());
        masterAttrMap.put(BigInteger.valueOf(4), master.getPicture());

        masterAttrMap.put(BigInteger.valueOf(5), master.getEnd_time());
        masterAttrMap.put(BigInteger.valueOf(6), master.getExperience());
        masterAttrMap.put(BigInteger.valueOf(7), master.getPayment());
        masterAttrMap.put(BigInteger.valueOf(8), master.getProfession());
        masterAttrMap.put(BigInteger.valueOf(9), master.getSkills());
        masterAttrMap.put(BigInteger.valueOf(10), master.isSmoke());
        persistenceEntity.setAttributes(masterAttrMap);
    }

    private void convertPokeToEntity(Poke poke, PersistenceEntity persistenceEntity)
    {
        HashMap<BigInteger, Object> pokeAttrMap = new HashMap<>();
        pokeAttrMap.put(BigInteger.valueOf(0), poke.getDescription());
        pokeAttrMap.put(BigInteger.valueOf(1), poke.getLocation());
        pokeAttrMap.put(BigInteger.valueOf(2), poke.getPassword());
        pokeAttrMap.put(BigInteger.valueOf(3), poke.getPhoneNumber());
        pokeAttrMap.put(BigInteger.valueOf(4), poke.getPicture());
        persistenceEntity.setAttributes(pokeAttrMap);

        HashMap<BigInteger, Long> orderRefMap = new HashMap<>();
        persistenceEntity.setReferences(orderRefMap);
    }

    private void convertUserToEntity(User user, PersistenceEntity persistenceEntity)
    {
        HashMap<BigInteger, Object> userAttrMap = new HashMap<>();
        userAttrMap.put(BigInteger.valueOf(0), user.getDescription());
        userAttrMap.put(BigInteger.valueOf(1), user.getLocation());
        userAttrMap.put(BigInteger.valueOf(2), user.getPassword());
        userAttrMap.put(BigInteger.valueOf(3), user.getPhoneNumber());
        userAttrMap.put(BigInteger.valueOf(4), user.getPicture());
        persistenceEntity.setAttributes(userAttrMap);

        HashMap<BigInteger, Long> orderRefMap = new HashMap<>();
        persistenceEntity.setReferences(orderRefMap);
    }

    private void convertAdminToEntity(Admin admin, PersistenceEntity persistenceEntity)
    {
        HashMap<BigInteger, Object> orderAttrMap = new HashMap<>();
        persistenceEntity.setAttributes(orderAttrMap);

        HashMap<BigInteger, Long> orderRefMap = new HashMap<>();
        persistenceEntity.setReferences(orderRefMap);
    }

    public <T extends BaseEntity> T convertToModel(PersistenceEntity persistenceEntity, final Class<? extends BaseEntity> CLASS)
    {
        T entity = null;

        if (Order.class.isAssignableFrom(CLASS))
            entity = convertToModelOrder(persistenceEntity);

        else if (Master.class.isAssignableFrom(CLASS))
            entity = convertToModelMaster(persistenceEntity);

        else if (Poke.class.isAssignableFrom(CLASS))
            entity = convertToModelPoke(persistenceEntity);

        else if (User.class.isAssignableFrom(CLASS))
            entity = convertToModelUser(persistenceEntity);

        else if (Admin.class.isAssignableFrom(CLASS))
            entity = convertToModelAdmin(persistenceEntity);

        entity.setObject_id(persistenceEntity.getObject_id());
        entity.setName(persistenceEntity.getName());
        entity.setDescription(persistenceEntity.getDescription());

        return entity;
    }

    private <T extends BaseEntity> T convertToModelOrder(PersistenceEntity persistenceEntity)
    {
        HashMap<BigInteger, Object> orderAttrMap = (HashMap)persistenceEntity.getAttributes();

        Order order = new Order();
        order.setBigDescription((String)orderAttrMap.get(BigInteger.valueOf(0)));
        order.setDueDate((Date)orderAttrMap.get(BigInteger.valueOf(1)));
        order.setSmallDescription((String)orderAttrMap.get(BigInteger.valueOf(2)));
        order.setStatus((String)orderAttrMap.get(BigInteger.valueOf(3)));
        order.setStartDate((Date)orderAttrMap.get(BigInteger.valueOf(4)));

        HashMap<BigInteger, Object> orderRefMap = (HashMap)persistenceEntity.getAttributes();
        //order.setMaster(orderRefMap.get(BigInteger.valueOf(0)));

        return (T)order;
    }

    private <T extends BaseEntity> T convertToModelMaster(PersistenceEntity persistenceEntity)
    {
        HashMap<BigInteger, Object> masterAttrMap = (HashMap)persistenceEntity.getAttributes();

        Master master = new Master();
        master.setDescription((String)masterAttrMap.get(BigInteger.valueOf(0)));
        master.setLocation((String)masterAttrMap.get(BigInteger.valueOf(1)));
        master.setPassword((String)masterAttrMap.get(BigInteger.valueOf(2)));
        master.setPhoneNumber((String)masterAttrMap.get(BigInteger.valueOf(3)));
        master.setPicture((String)masterAttrMap.get(BigInteger.valueOf(4)));
        master.setEnd_time((Date)masterAttrMap.get(BigInteger.valueOf(5)));
        master.setExperience((String)masterAttrMap.get(BigInteger.valueOf(6)));
        master.setPayment((int)masterAttrMap.get(BigInteger.valueOf(7)));
        master.setProfession((String)masterAttrMap.get(BigInteger.valueOf(8)));
        master.setSkills((String)masterAttrMap.get(BigInteger.valueOf(9)));
        master.setSmoke((boolean)masterAttrMap.get(BigInteger.valueOf(10)));

        HashMap<BigInteger, Object> masterRefMap = (HashMap)persistenceEntity.getAttributes();

        return (T)master;
    }

    private <T extends BaseEntity> T convertToModelPoke(PersistenceEntity persistenceEntity)
    {
        HashMap<BigInteger, Object> pokeAttrMap = (HashMap)persistenceEntity.getAttributes();

        Poke poke = new Poke();
        poke.setDescription((String)pokeAttrMap.get(BigInteger.valueOf(0)));
        poke.setLocation((String)pokeAttrMap.get(BigInteger.valueOf(1)));
        poke.setPassword((String)pokeAttrMap.get(BigInteger.valueOf(2)));
        poke.setPhoneNumber((String)pokeAttrMap.get(BigInteger.valueOf(3)));
        poke.setPicture((String)pokeAttrMap.get(BigInteger.valueOf(4)));

        HashMap<BigInteger, Object> pokeRefMap = (HashMap)persistenceEntity.getAttributes();
        return (T)poke;
    }

    private <T extends BaseEntity> T convertToModelUser(PersistenceEntity persistenceEntity)
    {
        HashMap<BigInteger, Object> userAttrMap = (HashMap)persistenceEntity.getAttributes();

        User user = new User();
        user.setDescription((String)userAttrMap.get(BigInteger.valueOf(0)));
        user.setLocation((String)userAttrMap.get(BigInteger.valueOf(1)));
        user.setPassword((String)userAttrMap.get(BigInteger.valueOf(2)));
        user.setPhoneNumber((String)userAttrMap.get(BigInteger.valueOf(3)));
        user.setPicture((String)userAttrMap.get(BigInteger.valueOf(4)));

        HashMap<BigInteger, Object> userRefMap = (HashMap)persistenceEntity.getAttributes();

        return (T)user;
    }

    private <T extends BaseEntity> T convertToModelAdmin(PersistenceEntity persistenceEntity)
    {
        HashMap<BigInteger, Object> orderAttrMap = (HashMap)persistenceEntity.getAttributes();

        HashMap<BigInteger, Object> orderRefMap = (HashMap)persistenceEntity.getAttributes();

        return (T)new Admin();
    }
}
