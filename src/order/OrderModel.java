package order;

import annotation.AttrType;
import annotation.ObjectType;
import master.Master;

import java.util.Date;


@ObjectType(OrderAttr.OBJTYPE)
public class OrderModel {

    @AttrType(OrderAttr.SMALL_DESCRIPTION)
    protected String SmallDescription;

    @AttrType(OrderAttr.BIG_DESCRIPTION)
    protected String BigDescription;

    @AttrType(OrderAttr.START_DATE)
    protected Date StartDate;

    @AttrType(OrderAttr.DUE_DATE)
    protected Date DueDate;

    @AttrType(OrderAttr.STATUS)
    protected String Status;

    @AttrType(OrderAttr.MASTER_REF)
    protected Master master;
}

