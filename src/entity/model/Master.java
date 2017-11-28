package entity.model;

import annotation.Attribute;
import annotation.ObjectType;
import entity.attr.MasterAttr;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@ObjectType(MasterAttr.OBJTYPE)
public class Master extends User {

    public interface Model extends MasterAttr{

    }
    @Attribute(Model.PROFESSION)
    protected String profession;

    @Attribute(Model.SKILLS)
    protected String skills;

    @Attribute(Model.EXPERIENCE)
    protected String experience;

    @Attribute(Model.PAYMENT)
    protected int payment;

    @Attribute(Model.SMOKE)
    protected boolean smoke;

    @Attribute(Model.TOOLS)
    protected String tools;

    @Attribute(Model.ST_TIME)
    protected Date start_time;

    @Attribute(Model.END_TIME)
    protected Date end_time;

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public boolean isSmoke() {
        return smoke;
    }

    public void setSmoke(boolean smoke) {
        this.smoke = smoke;
    }

    public String getTools() {
        return tools;
    }

    public void setTools(String tools) {
        this.tools = tools;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public void fillAttributeFields(HashMap<String, Object> hashMap)
    {
        super.fillAttributeFields(hashMap);

        Field sqcField[] = Master.class.getDeclaredFields();
        Attribute attrib;
        int length = sqcField.length;
        try
        {
            for (int i = 0; i < length; ++i)
            {
                attrib = sqcField[i].getAnnotation(Attribute.class);
                if (attrib != null)
                {
                    String attribValue = attrib.value();

                    if
                    (
                        attribValue.equals(Model.END_TIME)
                            ||
                        attribValue.equals(Model.ST_TIME)
                    )
                    {
                        try
                        {
                            sqcField[i].set(this, new SimpleDateFormat("dd/MM/yyyy HH:mm").parse((String) hashMap.get(attrib.value())));
                        }

                        catch (ParseException exc)
                        {
                            exc.printStackTrace();
                        }
                    }

                    else if (attribValue.equals(Model.PAYMENT))
                        sqcField[i].set(this, Integer.parseInt((String)hashMap.get(attrib.value())));

                    else if (attribValue.equals(Model.SMOKE))
                        sqcField[i].set(this, Boolean.parseBoolean((String)hashMap.get(attrib.value())));

                    else
                        sqcField[i].set(this, hashMap.get(attrib.value()));
                }
            }
        }

        catch (IllegalAccessException exc)
        {
            exc.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Master{"
                + super.toString()
                + "\nprofession='" + profession + '\'' +
                ", \nskills='" + skills + '\'' +
                ", \nexperience='" + experience + '\'' +
                ", \npayment=" + payment +
                ", \nsmoke=" + smoke +
                ", \ntools='" + tools + '\'' +
                ", \nstart_time=" + start_time +
                ", \nend_time=" + end_time +
                '}';
    }
}
