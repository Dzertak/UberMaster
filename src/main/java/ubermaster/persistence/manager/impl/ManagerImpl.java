package ubermaster.persistence.manager.impl;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ubermaster.annotation.ObjectType;
import ubermaster.entity.model.*;
import ubermaster.persistence.converter.impl.ConverterImpl;
import ubermaster.persistence.manager.Manager;
import ubermaster.persistence.manager.data.UberDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;

import static ubermaster.persistence.manager.data.UberDataSource.getConnection;


@Component
public class ManagerImpl implements Manager {
    @Autowired
    private UberDataSource dataSource;

    public void createEntity
            (
                    PersistenceEntity persistenceEntity,
                    final Class<? extends BaseEntity> CLASS
            ) {
        try {

            HashMap<String, Object> hashMap =
                    (HashMap<String, Object>) persistenceEntity.getAttributes();
            String[] elements = new String[4 + (hashMap.size() << 1)];
            elements[0] = Long.toString(persistenceEntity.getObject_id());
            elements[1] = CLASS.getAnnotation(ObjectType.class).value();
            elements[2] = persistenceEntity.getName();
            elements[3] = persistenceEntity.getDescription();

            int i = 4;
            Iterator<String> iterator = hashMap.keySet().iterator();
            while (iterator.hasNext()) {
                String attrID = iterator.next();
                elements[i] = attrID;
                ++i;
                elements[i] = ConverterImpl.convertObjectToString(hashMap.get(attrID));
                ++i;
            }


            OracleConnection oracleConnection = getConnection();

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
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }


    public PersistenceEntity getEntity(long id, final Class<? extends BaseEntity> CLASS) {
        OracleCallableStatement calStat;
        ResultSet resultSet;
        PersistenceEntity persistenceEntity = new PersistenceEntity();
        try {
            OracleConnection oracleConnection = getConnection();
            calStat = (OracleCallableStatement) oracleConnection.prepareCall(GET_ENTITY);
            calStat.setString(1, Long.toString(id));
            calStat.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
            calStat.execute();
            resultSet = calStat.getCursor(2);

            persistenceEntity.setObject_id(id);
            HashMap<String, Object> attrMap = new HashMap<>();
            while (resultSet.next()) {
                String attr_id = resultSet.getString(2);

                switch (attr_id) {
                    case ATTR_OBJECT_ID:
                    case ATTR_OBJECT_TYPE_ID:
                        break;
                    case ATTR_NAME:
                        persistenceEntity.setName(resultSet.getString(1));
                        break;

                    case ATTR_DESCR:
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
        } catch (SQLException | ParseException exc) {
            exc.printStackTrace();
            return null;
        }
        return persistenceEntity;
    }

    public PersistenceEntity getUser(String phoneNumber, String password) {
        OracleCallableStatement calStat;
        ResultSet resultSet;
        PersistenceEntity persistenceEntity = new PersistenceEntity();
        try {
            OracleConnection oracleConnection = getConnection();
            calStat = (OracleCallableStatement) oracleConnection.prepareCall(GET_USER);
            calStat.setString(1, phoneNumber);
            calStat.setString(2, password);
            calStat.registerOutParameter(3, oracle.jdbc.OracleTypes.CURSOR);
            calStat.execute();
            resultSet = calStat.getCursor(3);

            HashMap<String, Object> attrMap = new HashMap<>();
            Class<? extends BaseEntity> entityClassType = null;
            final String MASTER_OT = Master.class.getAnnotation(ObjectType.class).value();
            final String POKE_OT = Poke.class.getAnnotation(ObjectType.class).value();
            while (resultSet.next()) {
                String attr_id = resultSet.getString(2);

                switch (attr_id) {
                    case ATTR_OBJECT_ID:
                        persistenceEntity.setObject_id(Long.parseLong(resultSet.getString(1)));
                        break;

                    case ATTR_OBJECT_TYPE_ID:
                        String objectTypeID = resultSet.getString(1);

                        if (objectTypeID.equals(MASTER_OT))
                            entityClassType = Master.class;

                        else if (objectTypeID.equals(POKE_OT))
                            entityClassType = Poke.class;

                        else
                            entityClassType = Admin.class;

                        persistenceEntity.setClassType(entityClassType);
                        break;

                    case ATTR_NAME:
                        persistenceEntity.setName(resultSet.getString(1));
                        break;

                    case ATTR_DESCR:
                        persistenceEntity.setDescription(resultSet.getString(1));
                        break;

                    default:
                        Class fieldType = BaseEntity.getFieldType(attr_id, entityClassType);
                        Object fieldObj = ConverterImpl.convertStringToObject
                                (
                                        resultSet.getString(1),
                                        fieldType
                                );
                        attrMap.put(attr_id, fieldObj);
                }
            }

            persistenceEntity.setAttributes(attrMap);
        } catch (SQLException | ParseException exc) {
            exc.printStackTrace();
            return null;
        }
        return persistenceEntity;
    }

    public void deleteEntity(long id) {
        try {
            OracleConnection oracleConnection = getConnection();
            PreparedStatement statement = oracleConnection.prepareStatement(DELETE_ENTITY);
            statement.setString(1, Long.toString(id));
            statement.execute();
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }

    public void updateEntity
            (
                    PersistenceEntity persistenceEntity,
                    final Class<? extends BaseEntity> CLASS
            ) {

    }

}
