package ubermaster.entity.model;

import ubermaster.annotation.Attribute;
import ubermaster.annotation.ObjectType;
import ubermaster.entity.attr.MasterAttr;

import java.util.Date;
import java.util.HashMap;

@ObjectType(MasterAttr.OBJTYPE)
public class Master extends User
{
/*::|		FIELD		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
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
/*::|		CONSTRUCTOR		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
/*::|		SUB_CLASS		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
    public interface Model extends MasterAttr
    {                       }
/*::|		F / P		:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~:~*/
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

    @Override
    public HashMap getAllFields()
    {
        HashMap<String, Object> hashmap = (HashMap<String, Object>) super.getAllFields();

        hashmap.put(Model.PROFESSION, profession);
        hashmap.put(Model.SKILLS, skills);
        hashmap.put(Model.END_TIME, end_time);
        hashmap.put(Model.ST_TIME, start_time);
        hashmap.put(Model.EXPERIENCE, experience);
        hashmap.put(Model.SMOKE, smoke);
        hashmap.put(Model.PAYMENT, payment);
        hashmap.put(Model.TOOLS, tools);

        return hashmap;
    }

    @Override
    public String toString()
    {
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
