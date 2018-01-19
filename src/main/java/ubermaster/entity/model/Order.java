package ubermaster.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Attribute(Model.MASTER_PROFESSION)
    protected String masterProfession;

    @Attribute(Model.MARK)
    protected byte mark = -1;

    @Attribute(Model.COMMENT)
    protected String comment;

    @Attribute(Model.MASTER_END_DATE)
    protected Date masterEndDate;

    @Attribute(Model.POKE_ID)
    protected long pokeId;

    public interface Model extends OrderAttr {
    }

    public long getPokeId()
    {
        return pokeId;
    }

    public void setPokeId(long pokeId)
    {
        this.pokeId = pokeId;
    }

    public Date getMasterEndDate()
    {
        return masterEndDate;
    }

    public void setMasterEndDate(Date date)
    {
        masterEndDate = date;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public byte getMark()
    {
        return mark;
    }

    public void setMark(byte mark)
    {
        this.mark = mark;
    }

    public String getMasterProfession()
    {
        return masterProfession;
    }

    public void setMasterProfession(String masterProfession)
    {
        this.masterProfession = masterProfession;
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

    @JsonIgnore
    @Override
    public HashMap getAllFields() {
        HashMap<String, Object> hashmap = new HashMap<>();

        hashmap.put(Model.SMALL_DESCRIPTION, SmallDescription);
        hashmap.put(Model.BIG_DESCRIPTION, BigDescription);
        hashmap.put(Model.START_DATE, StartDate);
        hashmap.put(Model.DUE_DATE, DueDate);
        hashmap.put(Model.STATUS, Status);
        hashmap.put(Model.MASTER_PROFESSION, masterProfession);
        hashmap.put(Model.MARK, mark);
        hashmap.put(Model.COMMENT, comment);
        hashmap.put(Model.MASTER_END_DATE, masterEndDate);

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
                ", \nmaster profession : " + masterProfession +
                ", \nmark : " + mark +
                ", \ncomment : " + comment +
                ", \nmaster end date : " + masterEndDate +
                '}';
    }
}

