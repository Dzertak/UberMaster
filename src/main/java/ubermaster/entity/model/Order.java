package ubermaster.entity.model;

import ubermaster.annotation.Attribute;
import ubermaster.annotation.ObjectType;
import ubermaster.entity.attr.OrderAttr;

import java.util.Date;
import java.util.HashMap;


@ObjectType(OrderAttr.OBJTYPE)
public class Order extends BaseEntity {
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
    protected long master = -1;

    protected String masterName;

    public interface Model extends OrderAttr {
    }

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

    public long getMaster() {
        return master;
    }

    public void setMaster(long master) {
        this.master = master;
    }

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    @Override
    public HashMap getAllFields() {
        HashMap<String, Object> hashmap = new HashMap<>();

        hashmap.put(Model.SMALL_DESCRIPTION, SmallDescription);
        hashmap.put(Model.BIG_DESCRIPTION, BigDescription);
        hashmap.put(Model.START_DATE, StartDate);
        hashmap.put(Model.DUE_DATE, DueDate);
        hashmap.put(Model.STATUS, Status);

        return hashmap;
    }

    @Override
    public String toString() {
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

