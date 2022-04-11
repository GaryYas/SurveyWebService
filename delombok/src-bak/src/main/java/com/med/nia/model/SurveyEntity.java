package com.med.nia.model;


import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.util.Date;
import java.util.Objects;

@Data
@Entity
@Table(name = "Surveys")
/**
 * DataBase Entity for persisting survey
 */
public class SurveyEntity {

    @Id
    @GeneratedValue
    public int id;

    @Column(name = "sleep_rate")
    int sleepRate;

    @Column(name = "skin_condition")
    int skinCondition;

    @ManyToOne
    @JoinColumn(name = "user_id")
    UserEntity user;

    @CreatedDate
    @Column(name = "created_at")
    Date createdAt;


    public SurveyEntity() {
    }

    /**
     *
     * @param sleepRate sleep rate of the patient
     * @param skinCondition skin condition of the patient
     * @param user user who performed the survey
     * @param createdAt creation time of the survey
     */
    public SurveyEntity(int sleepRate, int skinCondition, UserEntity user, Date createdAt) {
        this.sleepRate = sleepRate;
        this.skinCondition = skinCondition;
        this.user = user;
        this.createdAt = createdAt;
    }

    /**
     * delete itself from user which the survey belongs to
     */
    public void deleteEntity(){
        this.user.removeSurveys(this);
        this.user = null;

    }

    public void setUser(UserEntity user){
        this.user = user;

        if(!user.getPerformedSurveys().contains(this))
            user.addSurvey(this);
    }
}
