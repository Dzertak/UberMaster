package entity.model;

import annotation.AttrType;
import annotation.ObjectType;
import entity.attr.OrderAttr;

import java.util.Date;


@ObjectType(OrderAttr.OBJTYPE)
public class Order {

    public interface Model extends OrderAttr{

    }
    @AttrType(Model.SMALL_DESCRIPTION)
    protected String SmallDescription;

    @AttrType(Model.BIG_DESCRIPTION)
    protected String BigDescription;

    @AttrType(Model.START_DATE)
    protected Date StartDate;

    @AttrType(Model.DUE_DATE)
    protected Date DueDate;

    @AttrType(Model.STATUS)
    protected String Status;

    @AttrType(Model.MASTER_REF)
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
    public String toString() {
        return "Order{" +
                "SmallDescription='" + SmallDescription + '\'' +
                ", BigDescription='" + BigDescription + '\'' +
                ", StartDate=" + StartDate +
                ", DueDate=" + DueDate +
                ", Status='" + Status + '\'' +
                ", master=" + master +
                '}';
    }
}

