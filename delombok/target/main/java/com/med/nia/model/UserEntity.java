package com.med.nia.model;

import lombok.*;
import javax.persistence.*;
import java.util.*;

/**
 * DataBase Entity for persisting User
 */
@Entity
@Table(name = "USERS")
public class UserEntity {
    @Id
    @GeneratedValue
    private int id;
    @Column
    String name;
    @Column(name = "user_name", unique = true)
    String userName;
    @Column
    int age;
    //,
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<SurveyEntity> performedSurveys = new HashSet<>();

    public UserEntity() {
    }

    /**
     * Entity contructer for the user
     *
     * @param name     of the user
     * @param userName of the user
     * @param age      of the use
     */
    public UserEntity(String name, String userName, int age) {
        this.name = name;
        this.userName = userName;
        this.age = age;
    }

    /**
     * clear and deletes all the surveys of this user
     */
    public void removeSurveys() {
        this.getPerformedSurveys().clear();
    }

    /**
     * removes the survey from the user for further deletion of the survey
     *
     * @param survey to be removed from this user
     */
    public void removeSurveys(SurveyEntity survey) {
        this.getPerformedSurveys().removeIf(surveyEntity -> surveyEntity.getId() == survey.getId());
    }

    public void addSurvey(SurveyEntity survey) {
        performedSurveys.add(survey);
        if (survey.getUser() != this) {
            survey.setUser(this);
        }
    }

    @SuppressWarnings("all")
    public int getId() {
        return this.id;
    }

    @SuppressWarnings("all")
    public String getName() {
        return this.name;
    }

    @SuppressWarnings("all")
    public String getUserName() {
        return this.userName;
    }

    @SuppressWarnings("all")
    public int getAge() {
        return this.age;
    }

    @SuppressWarnings("all")
    public Set<SurveyEntity> getPerformedSurveys() {
        return this.performedSurveys;
    }

    @SuppressWarnings("all")
    public void setId(final int id) {
        this.id = id;
    }

    @SuppressWarnings("all")
    public void setName(final String name) {
        this.name = name;
    }

    @SuppressWarnings("all")
    public void setUserName(final String userName) {
        this.userName = userName;
    }

    @SuppressWarnings("all")
    public void setAge(final int age) {
        this.age = age;
    }

    @SuppressWarnings("all")
    public void setPerformedSurveys(final Set<SurveyEntity> performedSurveys) {
        this.performedSurveys = performedSurveys;
    }
}
