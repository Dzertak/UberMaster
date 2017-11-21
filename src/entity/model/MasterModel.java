package entity.model;

import annotation.AttrType;
import annotation.ObjectType;
import entity.attr.MasterAttr;

import java.util.Date;

@ObjectType(MasterAttr.OBJTYPE)
public class MasterModel extends UserModel {

@AttrType(MasterAttr.PROFESSION)
protected String profession;

@AttrType(MasterAttr.SKILLS)
protected String skills;

@AttrType(MasterAttr.EXPERIENCE)
protected String experience;

@AttrType(MasterAttr.PAYMENT)
protected int payment;

@AttrType(MasterAttr.SMOKE)
protected boolean smoke;

@AttrType(MasterAttr.TOOLS)
protected String tools;

@AttrType(MasterAttr.ST_TIME)
protected Date start_time;

@AttrType(MasterAttr.END_TIME)
protected Date end_time;
}
