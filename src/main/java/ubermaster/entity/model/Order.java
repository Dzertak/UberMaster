package ubermaster.entity.model;

import ubermaster.annotation.Attribute;
import ubermaster.annotation.ObjectType;
import ubermaster.entity.attr.OrderAttr;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;


@ObjectType(OrderAttr.OBJTYPE)
public class Order extends BaseEntity
{
    public interface Model extends OrderAttr
    {                   }

    @Attribute(Model.SMALL_DESCRIPTION)
    protected String SmallDescription;

    @Attribute(Model.BIG_DESCRIPTION)
    protected String BigDescription;

    @Attribute(Model.START_DATE)
    protected Date StartDate;

    @Attribute(Model.DUE_DATE)
    protected Date DueDate;

    @Attribute(Model.STATUS)
    protected String Status;

    @Attribute(Model.MASTER_REF)
    protected Master master;

    public String getSmallDescription() {
        return SmallDescription;
    }

    public void setSmallDescription(String smallDescription) {
        SmallDescription = smallDescription;
    }

    public String getBigDescription() {
        return BigDescription;
    }

    public void setBigDescription(String bigDescription) {
        BigDescription = bigDescription;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public void setStartDate(Date startDate) {
        StartDate = startDate;
    }

    public Date getDueDate() {
        return DueDate;
    }

    public void setDueDate(Date dueDate) {
        DueDate = dueDate;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Master getMaster() {
        return master;
    }

    public void setMaster(Master master) {
        this.master = master;
    }

    @Override
    public void fillAttributeFields(HashMap<String, Object> hashMap)
    {
        Field sqcField[] = Order.class.getDeclaredFields();
        Attribute attrib;
        int length = sqcField.length;
        try
        {
            for (int i = 0; i < length; ++i)
            {
                attrib = sqcField[i].getAnnotation(Attribute.class);

                if (attrib != null)
                {
                    if (!attrib.value().equals(Model.MASTER_REF))
                        setField(sqcField[i], (String)hashMap.get(attrib.value()), this);
                }
            }
        }

        catch (IllegalAccessException | ParseException exc)
        {
            exc.printStackTrace();
        }
    }

    @Override
    public HashMap getAllFields()
    {
        HashMap<String, Object> hashmap = new HashMap<>();

        hashmap.put(Model.SMALL_DESCRIPTION, SmallDescription);
        hashmap.put(Model.BIG_DESCRIPTION, BigDescription);
        hashmap.put(Model.START_DATE, StartDate);
        hashmap.put(Model.DUE_DATE, DueDate);
        hashmap.put(Model.STATUS, Status);

        return hashmap;
    }

    @Override
    public String toString()
    {
        return "Order{"
                + super.toString()
                + "\nSmallDescription='" + SmallDescription + '\'' +
                ", \nBigDescription='" + BigDescription + '\'' +
                ", \nStartDate=" + StartDate +
                ", \nDueDate=" + DueDate +
                ", \nStatus='" + Status + '\'' +
                ", \nmaster=" + master +
                '}';
    }
}

