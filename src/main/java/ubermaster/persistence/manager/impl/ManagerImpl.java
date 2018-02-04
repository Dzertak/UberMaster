package ubermaster.persistence.manager.impl;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import ubermaster.annotation.ObjectType;
import ubermaster.entity.model.*;
import ubermaster.persistence.converter.impl.ConverterImpl;
import ubermaster.persistence.manager.Manager;

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
    /*private boolean isValid(Object object)
    {
        if (object == null)
            return false;

        try
        {
            if (object instanceof Number && ((Number)object).longValue() == -1)
                return false;
        }

        catch (ClassCastException exc)
        {
            exc.printStackTrace();
        }

        return true;
    }*/

    public void createEntity
    (
        PersistenceEntity persistenceEntity,
        Class<? extends BaseEntity> _class
    )
    {
        OracleConnection oracleConnection = null;
        OracleCallableStatement oraCallStat = null;
        try
        {
            HashMap<String, Object> hashMap =
                    (HashMap<String, Object>) persistenceEntity.getAttributes();
            String[] elements = new String[3     + (hashMap.size() << 1)];
            elements[0] = _class.getAnnotation(ObjectType.class).value();
            elements[1] = persistenceEntity.getName();
            elements[2] = persistenceEntity.getDescription();

            int i = 3;
            Iterator<String> iterator = hashMap.keySet().iterator();
            while (iterator.hasNext())
            {
                String attrID = iterator.next();
                //if (isValid(hashMap.get(attrID)))
                {
                    elements[i] = attrID;
                    ++i;
                    elements[i] = ConverterImpl.convertObjectToString(hashMap.get(attrID));
                    ++i;
                }
            }

            oracleConnection = getConnection();
            ArrayDescriptor descriptor = ArrayDescriptor.createDescriptor
                    (
                        ARRAY,
                        oracleConnection
                    );
            ARRAY array = new ARRAY(descriptor, oracleConnection, elements);

            oraCallStat = (OracleCallableStatement) oracleConnection.prepareCall
                    (
                        INSERT_ENTITY
                    );
            oraCallStat.setARRAY(1, array);
            oraCallStat.execute();
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
                oraCallStat.close();
                oracleConnection.close();
            }

            catch (SQLException exc)
            {
                log.error(exc.getMessage(), exc);
                //exc.printStackTrace();
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
        OracleCallableStatement callStat = null;
        ResultSet resultSet;
        OracleConnection oracleConnection = null;
        try
        {
            oracleConnection = getConnection();
            callStat = (OracleCallableStatement) oracleConnection.prepareCall(GET_ENTITY);
            callStat.setString(1, Long.toString(id));
            callStat.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
            callStat.execute();
            resultSet = callStat.getCursor(2);

            return getPersistanceEntity(resultSet);
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
                callStat.close();
                oracleConnection.close();
            }

            catch (SQLException exc)
            {
                log.error(exc.getMessage(), exc);
                //exc.printStackTrace();
            }
        }
    }

    public PersistenceEntity[] getTypedEntities(Class<? extends BaseEntity> _class)
    {

        String objectTypeID = _class.getAnnotation(ObjectType.class).value();
        OracleConnection connection = null;
        OracleCallableStatement callStat = null;
        try
        {
            connection = getConnection();
            callStat = (OracleCallableStatement) connection.prepareCall(GET_TYPED_ENTITIES);
            callStat.registerOutParameter(2, OracleTypes.ARRAY, ARRAY_ENTITIES);
            callStat.setString(1, objectTypeID);
            callStat.execute();

            return getPersistanceEntities
                    (
                            (Object[]) callStat.getARRAY(2).getArray(),
                            _class
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
                callStat.close();
                connection.close();
            }

            catch (SQLException exc)
            {
                log.error(exc.getMessage(), exc);
                //exc.printStackTrace();
            }
        }
    }

    public PersistenceEntity getUserByPhone(String phoneNumber)
    {
        OracleCallableStatement callStat = null;
        ResultSet resultSet;
        OracleConnection oracleConnection = null;
        try
        {
            oracleConnection = getConnection();
            callStat = (OracleCallableStatement) oracleConnection
                    .prepareCall(GET_USER_BY_PHONE);
            callStat.setString(1, phoneNumber);
            callStat.registerOutParameter
                    (
                            2,
                            oracle.jdbc.OracleTypes.CURSOR
                    );
            callStat.execute();
            resultSet = callStat.getCursor(2);

            return getPersistanceEntity(resultSet);
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
                callStat.close();
                oracleConnection.close();
            }

            catch (SQLException exc)
            {
                log.error(exc.getMessage(), exc);
                //exc.printStackTrace();
            }
        }
    }

    public PersistenceEntity getUserByPhonePass(String phoneNumber, String password)
    {
        OracleCallableStatement callStat = null;
        ResultSet resultSet;
        OracleConnection oracleConnection = null;
        try
        {
            oracleConnection = getConnection();
            callStat = (OracleCallableStatement) oracleConnection
                    .prepareCall(GET_USER);
            callStat.setString(1, phoneNumber);
            callStat.setString(2, password);
            callStat.registerOutParameter
                    (
                            3,
                            oracle.jdbc.OracleTypes.CURSOR
                    );
            callStat.execute();
            resultSet = callStat.getCursor(3);

            return getPersistanceEntity(resultSet);
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
                callStat.close();
                oracleConnection.close();
            }

            catch (SQLException exc)
            {
                log.error(exc.getMessage(), exc);
                //exc.printStackTrace();
            }
        }
    }

    public PersistenceEntity[] getUserOrders(long id, int userType)
    {
        OracleCallableStatement callStat = null;
        OracleConnection connection = null;
        PersistenceEntity sqcPE[];
        try
        {
            connection = getConnection();//dataSource.getConnection();
            
            switch (userType)
            {
                case MASTER_TYPE_ORDERS :
                    callStat = (OracleCallableStatement) connection.prepareCall(GET_MASTER_ORDERS);
                break;
                
                case POKE_TYPE_ORDERS :
                    callStat = (OracleCallableStatement) connection.prepareCall(GET_POKE_ORDERS);
                break;
                
                default :
                    callStat = null;
            }

            callStat.registerOutParameter(2, OracleTypes.ARRAY, ARRAY_ENTITIES);

            callStat.setString(1, Long.toString(id));
            callStat.execute();

            return getPersistanceEntities
                    (
                        (Object[]) callStat.getARRAY(2).getArray(),
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
                callStat.close();
                connection.close();
            }

            catch (SQLException exc)
            {
                log.error(exc.getMessage(), exc);
                //exc.printStackTrace();
            }
        }
    }

    public PersistenceEntity[] getOrdersByParam(final byte CON_LST_VAL, String value)
    {
        OracleCallableStatement callStat = null;
        OracleConnection connection = null;
        try
        {
            connection = getConnection();

            switch (CON_LST_VAL)
            {
                case CON_LST_PROFESSION :
                    callStat = (OracleCallableStatement) connection.prepareCall(GET_ORDER_BY_PROFESSION);
                break;

                case CON_LST_STATUS :
                    callStat = (OracleCallableStatement) connection.prepareCall(GET_ORDER_BY_STATUS);
                break;

                case CON_ATTR_SORT :
                    callStat = (OracleCallableStatement) connection.prepareCall(GET_ORDER_SORT);
                break;

                default :
                    callStat = null;
            }

            callStat.registerOutParameter(2, OracleTypes.ARRAY, ARRAY_ENTITIES);

            callStat.setString(1, value);
            callStat.execute();

            return getPersistanceEntities
                (
                    (Object[]) callStat.getARRAY(2).getArray(),
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
                callStat.close();
                connection.close();
            }

            catch (SQLException exc)
            {
                log.error(exc.getMessage(), exc);
                //exc.printStackTrace();
            }
        }
    }

    public String simpleQuery(final byte CON_QUERY_VAL, long id)
	{
		OracleConnection oracleConnection = null;
		PreparedStatement statement = null;
		try
		{
			oracleConnection = getConnection();

			switch (CON_QUERY_VAL)
			{
				case CON_DELETE :
					statement = oracleConnection.prepareStatement(DELETE_ENTITY);
				break;

				case CON_MASTER_AVER :
					statement = oracleConnection.prepareStatement(GET_MASTER_AVER_MARK);
				break;

                case CON_MASTER_NAME :
                    statement = oracleConnection.prepareStatement(GET_MASTER_NAME);
                break;

                case CON_POKE_ID :
                    statement = oracleConnection.prepareStatement(GET_POKE_ID);
                break;

                case CON_BUSER_STATUS :
                    statement = oracleConnection.prepareStatement(GET_BUSER_STATUS);
                break;

                default :
                    statement = null;
			}

			statement.setString(1, Long.toString(id));
			statement.execute();

			ResultSet resultSet = statement.getResultSet();

			if (resultSet == null)
				return null;

			resultSet.next();
			return resultSet.getString(1);
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
				statement.close();
				oracleConnection.close();
			}

			catch (SQLException exc)
			{
				log.error(exc.getMessage(), exc);
				//exc.printStackTrace();
			}
		}

		return null;
	}

    public void updateEntity(long id, Object ... sqcParam)
    {
        OracleConnection oracleConnection = null;
        OracleCallableStatement callStat = null;
        try
        {
            String elements[] = new String[1 + sqcParam.length];
            final int LEN_ELEMENTS = elements.length;

            elements[0] = Long.toString(id);
            for (int i = 1; i < LEN_ELEMENTS;)
            {
                //if (isValid(sqcParam[i]))
                {
                    elements[i] = (String)sqcParam[i - 1];
                    ++i;
                    elements[i] = ConverterImpl.convertObjectToString(sqcParam[i - 1]);
                    ++i;
                }

                /*else
                    i += 2;*/
               /* elements[i] = (String)sqcParam[i - 1];
                ++i;
                elements[i] = ConverterImpl.convertObjectToString(sqcParam[i - 1]);
                ++i;*/
            }

            oracleConnection = getConnection();
            ArrayDescriptor descriptor = ArrayDescriptor.createDescriptor
                    (
                            ARRAY,
                            oracleConnection
                    );
            ARRAY array = new ARRAY(descriptor, oracleConnection, elements);

            callStat = (OracleCallableStatement) oracleConnection.prepareCall
                    (
                        UPDATE_ENTITY
                    );
            callStat.setARRAY(1, array);
            callStat.execute();
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
                callStat.close();
                oracleConnection.close();
            }

            catch (SQLException exc)
            {
                log.error(exc.getMessage(), exc);
                //exc.printStackTrace();
            }
        }
    }

    public String[] getMasterComments(long id, int count)
    {
        OracleConnection connection = null;
        OracleCallableStatement callStat = null;
        try
        {
            connection = getConnection();
            callStat = (OracleCallableStatement) connection.prepareCall(GET_MASTER_COMMENTS);
            callStat.registerOutParameter(3, OracleTypes.ARRAY, ARRAY);
            callStat.setLong(1, id);
            callStat.setInt(2, count);
            callStat.execute();

            return (String[])callStat.getARRAY(3).getArray();
        }

        catch (SQLException exc)
        {
            log.error(exc.getMessage(), exc);
            //exc.printStackTrace();
            return null;
        }

        finally
        {
            try
            {
                callStat.close();
                connection.close();
            }

            catch (SQLException exc)
            {
                log.error(exc.getMessage(), exc);
                //exc.printStackTrace();
            }
        }
    }
}
