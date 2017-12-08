package ubermaster.persistence.manager.impl;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.pool.OracleDataSource;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ubermaster.annotation.ObjectType;
import ubermaster.entity.model.BaseEntity;
import ubermaster.persistence.PersistenceEntity;
import ubermaster.persistence.converter.impl.ConverterImpl;
import ubermaster.persistence.manager.Manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;

import static ubermaster.OracleConnector.getConnection;

@Component
public class ManagerImpl implements Manager
{
/*::|       FIELD       :~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:*/
    @Autowired
    private OracleDataSource dataSource;
/*::|       CONSTRUCTOR       :~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:*/
/*::|       SUB_CLASS       :~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:*/
/*::|       F / P       :~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:*/
    public void createEntity
    (
        PersistenceEntity persistenceEntity,
        final Class<? extends BaseEntity> CLASS
    )
    {
        try
        {
            HashMap<String, Object> hashMap =
                        (HashMap<String, Object>) persistenceEntity.getAttributes();
            String[] elements = new String[4 + (hashMap.size() << 1)];
            elements[0] = Long.toString(persistenceEntity.getObject_id());
            elements[1] = CLASS.getAnnotation(ObjectType.class).value();
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

            //Connection connection = dataSource.getConnection();
            Connection connection = getConnection();
            ArrayDescriptor descriptor = ArrayDescriptor.createDescriptor
                                                (
                                                    "ARRAY",
                                                    connection
                                                );
            ARRAY array = new ARRAY(descriptor, connection, elements);

            OracleCallableStatement stmt = (OracleCallableStatement)connection.prepareCall
                                            (
                                                INSERT_ENTITY
                                            );
            stmt.setARRAY(1, array);
            stmt.execute();
        }

        catch (SQLException exc)
        {
            exc.printStackTrace();
        }
    }


    public PersistenceEntity getEntity(long id, final Class<? extends BaseEntity> CLASS)
    {
        OracleCallableStatement calStat;
        ResultSet resultSet;
        PersistenceEntity persistenceEntity = new PersistenceEntity();
        try
        {
            Connection connection = getConnection();//dataSource.getConnection();
            calStat = (OracleCallableStatement) connection.prepareCall(GET_ENTITY);
            calStat.setString(1, Long.toString(id));
            calStat.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
            calStat.execute();
            resultSet = calStat.getCursor(2);

            persistenceEntity.setObject_id(id);
            HashMap<String, Object> attrMap = new HashMap<>();
            while (resultSet.next()) {
                String attr_id = resultSet.getString(2);

                switch (attr_id)
                {
                    case ATTR_NAME :
                        persistenceEntity.setName(resultSet.getString(1));
                        break;

                    case ATTR_DESCR :
                        persistenceEntity.setDescription(resultSet.getString(1));
                        break;

                    default:
                        Class fieldType = BaseEntity.getFieldType(attr_id, CLASS);
                        Object fieldObj = ConverterImpl.convertStringToObject
                                    (
                                        resultSet.getString(1),
                                        fieldType
                                    );
                        attrMap.put(attr_id, fieldObj);
                }
            }

            persistenceEntity.setAttributes(attrMap);
        }

        catch (SQLException | ParseException exc)
        {
            exc.printStackTrace();
        }

        return persistenceEntity;
    }

    public void deleteEntity(long id)
    {
        Connection connection = getConnection();

        try
        {
            PreparedStatement statement = connection.prepareStatement(DELETE_ENTITY);
            statement.setString(1, Long.toString(id));
            statement.execute();
        }

        catch (SQLException exc)
        {
            exc.printStackTrace();
        }
    }
}
