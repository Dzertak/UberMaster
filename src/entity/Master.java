package entity;

import entity.model.MasterModel;

import java.util.Date;

public class Master extends MasterModel {


    public String getProfession() {
        return profession;
    }

    public String getSkills() {
        return skills;
    }

    public String getExperience() {
        return experience;
    }

    public int getPayment() {
        return payment;
    }

    public boolean isSmoke() {
        return smoke;
    }

    public String getTools() {
        return tools;
    }

    public Date getStart_time() {
        return start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }
}
