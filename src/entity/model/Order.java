package entity.model;

import annotation.Attribute;
import annotation.ObjectType;
import entity.attr.OrderAttr;

import java.util.Date;


@ObjectType(OrderAttr.OBJTYPE)
public class Order extends BaseEntity
{
    public interface Model extends OrderAttr{

    }
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

