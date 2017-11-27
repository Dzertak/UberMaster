package entity.model;

import annotation.AttrType;
import annotation.ObjectType;
import entity.attr.MasterAttr;

import java.util.Date;

@ObjectType(MasterAttr.OBJTYPE)
public class Master extends User {

    public interface Model extends MasterAttr{

    }
    @AttrType(Model.PROFESSION)
    protected String profession;

    @AttrType(Model.SKILLS)
    protected String skills;

    @AttrType(Model.EXPERIENCE)
    protected String experience;

    @AttrType(Model.PAYMENT)
    protected int payment;

    @AttrType(Model.SMOKE)
    protected boolean smoke;

    @AttrType(Model.TOOLS)
    protected String tools;

    @AttrType(Model.ST_TIME)
    protected Date start_time;

    @AttrType(Model.END_TIME)
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

    @Override
    public String toString() {
        return "Master{" +
                "profession='" + profession + '\'' +
                ", skills='" + skills + '\'' +
                ", experience='" + experience + '\'' +
                ", payment=" + payment +
                ", smoke=" + smoke +
                ", tools='" + tools + '\'' +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                '}';
    }
}
