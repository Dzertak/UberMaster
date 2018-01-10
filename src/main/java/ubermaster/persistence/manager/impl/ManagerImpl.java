package ubermaster.persistence.manager.impl;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ubermaster.annotation.ObjectType;
import ubermaster.entity.model.*;
import ubermaster.persistence.converter.impl.ConverterImpl;
import ubermaster.persistence.manager.Manager;
import ubermaster.persistence.manager.data.UberDataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;

import static ubermaster.persistence.manager.data.UberDataSource.getConnection;

/**
 * @author Serpye 
 * 
 * The {@code ManagerImpl} _class is realization of {@code Manager} interface
 * */
@Component
public class ManagerImpl implements Manager
{
    /*::|       FIELD       :~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
    private static Logger log = Logger.getLogger(ManagerImpl.class.getName());
    /*::|       CONSTRUCTOR     :~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
/*::|       SUB_CLASS       :~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
/*::|       F / P       :~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
    public void createEntity
    (
            PersistenceEntity persistenceEntity,
            Class<? extends BaseEntity> _class
    )
    {
        OracleConnection oracleConnection = null;
        try
        {
            HashMap<String, Object> hashMap =
                    (HashMap<String, Object>) persistenceEntity.getAttributes();
            String[] elements = new String[4 + (hashMap.size() << 1)];
            elements[0] = Long.toString(persistenceEntity.getObject_id());
            elements[1] = _class.getAnnotation(ObjectType.class).value();
            elements[2] = persistenceEntity.getName();
            elements[3] = persistenceEntity.getDescription();

            int i = 4;
            Iterator<String> iterator = hashMap.keySet().iterator();
            while (iterator.hasNext())
            {
                String attrID = iterator.next();
                elements[i] = attrID;
                ++i;
                elements[i] = ConverterImpl.convertObjectToString(hashMap.get(attrID));
                ++i;
            }

            oracleConnection = getConnection();
            ArrayDescriptor descriptor = ArrayDescriptor.createDescriptor
                    (
                            "ARRAY",
                            oracleConnection
                    );
            ARRAY array = new ARRAY(descriptor, oracleConnection, elements);

            OracleCallableStatement stmt = (OracleCallableStatement) oracleConnection.prepareCall
                    (
                            INSERT_ENTITY
                    );
            stmt.setARRAY(1, array);
            stmt.execute();
        }

        catch (SQLException exc)
        {
            //log.error(exc.getMessage(), exc);
            exc.printStackTrace();
        }

        finally
        {
            try
            {
                oracleConnection.close();
            }

            catch (SQLException exc)
            {
                //log.error(exc.getMessage(), exc);
                exc.printStackTrace();
            }
        }
    }

    private PersistenceEntity getPersistanceEntity(ResultSet resultSet)
            throws SQLException, ParseException
    {
        PersistenceEntity persistenceEntity = new PersistenceEntity();

        HashMap<String, Object> attrMap = new HashMap<>();
        Class<? extends BaseEntity> entity_classType = null;

        final String MASTER_OT = Master.class.getAnnotation(ObjectType.class).value();
        final String POKE_OT = Poke.class.getAnnotation(ObjectType.class).value();
        final String ADMIN_OT = Admin.class.getAnnotation(ObjectType.class).value();
        final String ORDER_OT = Order.class.getAnnotation(ObjectType.class).value();
        while (resultSet.next())
        {
            String attr_id = resultSet.getString(2);

            switch (attr_id)
            {
                case ATTR_OBJECT_ID:
                    persistenceEntity.setObject_id(Long.parseLong(resultSet.getString(1)));
                    break;

                case ATTR_OBJECT_TYPE_ID:
                    String objectTypeID = resultSet.getString(1);

                    if (objectTypeID.equals(ORDER_OT))
                        entity_classType = Order.class;

                    else if (objectTypeID.equals(MASTER_OT))
                        entity_classType = Master.class;

                    else if (objectTypeID.equals(POKE_OT))
                        entity_classType = Poke.class;

                    else if (objectTypeID.equals(ADMIN_OT))
                        entity_classType = Admin.class;

                    persistenceEntity.setClassType(entity_classType);
                break;

                case ATTR_NAME:
                    persistenceEntity.setName(resultSet.getString(1));
                    break;

                case ATTR_DESCR:
                    persistenceEntity.setDescription(resultSet.getString(1));
                    break;

                default:
                    Class fieldType = BaseEntity.getFieldType(attr_id, entity_classType);
                    Object fieldObj = ConverterImpl.convertStringToObject
                            (
                                    resultSet.getString(1),
                                    fieldType
                            );
                    attrMap.put(attr_id, fieldObj);
            }
        }

        persistenceEntity.setAttributes(attrMap);

        return persistenceEntity;
    }

    private PersistenceEntity[] getPersistanceEntities
            (
                    Object sqcEntity[],
                    Class<? extends BaseEntity> _class
            )
            throws SQLException, ParseException
    {
        int lengthEntity = sqcEntity.length;
        PersistenceEntity sqcPE[] = new PersistenceEntity[lengthEntity];
        for (int iteraEntity = 0; iteraEntity < lengthEntity; ++iteraEntity)
        {
            PersistenceEntity persistenceEntity = new PersistenceEntity();
            persistenceEntity.setClassType(_class);
            HashMap<String, Object> attrMap = new HashMap<>();

            Object sqcField[] = (Object[]) ((ARRAY)sqcEntity[iteraEntity]).getArray();
            int lengthField = sqcField.length;
            //--:   Field cycle
            for (int iteraField = 0; iteraField < lengthField; ++iteraField)
            {
                Object sqcAttr_Val[] = ((STRUCT)sqcField[iteraField]).getAttributes();

                String attrID = (String)sqcAttr_Val[0];
                String value = (String)sqcAttr_Val[1];

                switch (attrID)
                {
                    case ATTR_OBJECT_ID :
                        persistenceEntity.setObject_id(Long.parseLong(value));
                        break;

                    case ATTR_NAME :
                        persistenceEntity.setName(value);
                        break;

                    case ATTR_DESCR :
                        persistenceEntity.setDescription(value);
                        break;

                    default :
                        Class fieldType = BaseEntity.getFieldType
                                (
                                        attrID,
                                        _class
                                );
                        Object fieldObj = ConverterImpl.convertStringToObject
                                (
                                        value,
                                        fieldType
                                );
                        attrMap.put(attrID, fieldObj);
                }
            }

            persistenceEntity.setAttributes(attrMap);
            sqcPE[iteraEntity] = persistenceEntity;
        }

        return sqcPE;
    }

    public PersistenceEntity getEntity(long id, Class<? extends BaseEntity> _class)
    {
        OracleCallableStatement calStat;
        ResultSet resultSet;
        OracleConnection oracleConnection = null;
        try
        {
            oracleConnection = getConnection();
            calStat = (OracleCallableStatement) oracleConnection.prepareCall(GET_ENTITY);
            calStat.setString(1, Long.toString(id));
            calStat.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
            calStat.execute();
            resultSet = calStat.getCursor(2);

            return getPersistanceEntity(resultSet);
        }

        catch (SQLException | ParseException exc)
        {
            //log.error(exc.getMessage(), exc);
            exc.printStackTrace();
            return null;
        }

        finally
        {
            try
            {
                oracleConnection.close();
            }

            catch (SQLException exc)
            {
                //log.error(exc.getMessage(), exc);
                exc.printStackTrace();
            }
        }
    }

    public PersistenceEntity[] getTypedEntities(Class<? extends BaseEntity> _class)
    {

        String objectTypeID = _class.getAnnotation(ObjectType.class).value();
        OracleConnection connection = null;
        OracleCallableStatement calStat;
        try
        {
            connection = getConnection();
            calStat = (OracleCallableStatement) connection.prepareCall(GET_TYPED_ENTITIES);
            calStat.registerOutParameter(2, OracleTypes.ARRAY, ARRAY_ENTITIES);
            calStat.setString(1, objectTypeID);
            calStat.execute();

            return getPersistanceEntities
                    (
                            (Object[]) calStat.getARRAY(2).getArray(),
                            _class
                    );
        }

        catch (SQLException | ParseException exc)
        {
            //log.error(exc.getMessage(), exc);
            exc.printStackTrace();
            return null;
        }

        finally
        {
            try
            {
                connection.close();
            }

            catch (SQLException exc)
            {
                //log.error(exc.getMessage(), exc);
                exc.printStackTrace();
            }
        }
    }

    public PersistenceEntity getUserByPhone(String phoneNumber)
    {
        OracleCallableStatement calStat;
        ResultSet resultSet;
        OracleConnection oracleConnection = null;
        try
        {
            oracleConnection = getConnection();
            calStat = (OracleCallableStatement) oracleConnection
                    .prepareCall(GET_USER_BY_PHONE);
            calStat.setString(1, phoneNumber);
            calStat.registerOutParameter
                    (
                            2,
                            oracle.jdbc.OracleTypes.CURSOR
                    );
            calStat.execute();
            resultSet = calStat.getCursor(2);

            return getPersistanceEntity(resultSet);
        }

        catch (SQLException | ParseException exc)
        {
            //log.error(exc.getMessage(), exc);
            exc.printStackTrace();
            return null;
        }

        finally
        {
            try
            {
                oracleConnection.close();
            }

            catch (SQLException exc)
            {
                //log.error(exc.getMessage(), exc);
                exc.printStackTrace();
            }
        }
    }

    public PersistenceEntity getUserByPhonePass(String phoneNumber, String password)
    {
        OracleCallableStatement calStat;
        ResultSet resultSet;
        OracleConnection oracleConnection = null;
        try
        {
            oracleConnection = getConnection();
            calStat = (OracleCallableStatement) oracleConnection
                    .prepareCall(GET_USER);
            calStat.setString(1, phoneNumber);
            calStat.setString(2, password);
            calStat.registerOutParameter
                    (
                            3,
                            oracle.jdbc.OracleTypes.CURSOR
                    );
            calStat.execute();
            resultSet = calStat.getCursor(3);

            return getPersistanceEntity(resultSet);
        }

        catch (SQLException | ParseException exc)
        {
            //log.error(exc.getMessage(), exc);
            exc.printStackTrace();
            return null;
        }

        finally
        {
            try
            {
                oracleConnection.close();
            }

            catch (SQLException exc)
            {
                //log.error(exc.getMessage(), exc);
                exc.printStackTrace();
            }
        }
    }

    public PersistenceEntity[] getPokeOrders(long id)
    {
        OracleCallableStatement calStat;
        OracleConnection connection = null;
        PersistenceEntity sqcPE[];
        try
        {
            connection = getConnection();//dataSource.getConnection();
            calStat = (OracleCallableStatement) connection.prepareCall(GET_POKE_ORDERS);

            calStat.registerOutParameter(2, OracleTypes.ARRAY, ARRAY_ENTITIES);

            calStat.setString(1, Long.toString(id));
            calStat.execute();

            return getPersistanceEntities
                    (
                            (Object[]) calStat.getARRAY(2).getArray(),
                            Order.class
                    );
        }

        catch (SQLException | ParseException exc)
        {
            log.error(exc.getMessage(), exc);
            //exc.printStackTrace();
            return null;
        }

        finally
        {
            try
            {
                connection.close();
            }

            catch (SQLException exc)
            {
                log.error(exc.getMessage(), exc);
                //exc.printStackTrace();
            }
        }
    }

    public PersistenceEntity[] getOrdersByProfession(String profession)
    {
        OracleCallableStatement calStat;
        PersistenceEntity sqcPE[];
        OracleConnection connection = null;
        try
        {
            connection = getConnection();//dataSource.getConnection();
            calStat = (OracleCallableStatement) connection.prepareCall(GET_ORDER_BY_PROFESSION);

            calStat.registerOutParameter(2, OracleTypes.ARRAY, ARRAY_ENTITIES);

            calStat.setString(1, profession);
            calStat.execute();

            return getPersistanceEntities
                    (
                            (Object[]) calStat.getARRAY(2).getArray(),
                            Order.class
                    );
        }

        catch (SQLException | ParseException exc)
        {
            //log.error(exc.getMessage(), exc);
            exc.printStackTrace();
            return null;
        }

        finally
        {
            try
            {
                connection.close();
            }

            catch (SQLException exc)
            {
                //log.error(exc.getMessage(), exc);
                exc.printStackTrace();
            }
        }
    }

    public void deleteEntity(long id)
    {
        OracleConnection oracleConnection = null;
        try
        {
            oracleConnection = getConnection();
            PreparedStatement statement = oracleConnection
                    .prepareStatement(DELETE_ENTITY);
            statement.setString(1, Long.toString(id));
            statement.execute();
        }

        catch (SQLException exc)
        {
            log.error(exc.getMessage(), exc);
            //exc.printStackTrace();
        }

        finally
        {
            try
            {
                oracleConnection.close();
            }

            catch (SQLException exc)
            {
                log.error(exc.getMessage(), exc);
                //exc.printStackTrace();
            }
        }
    }
}
