package com.med.nia.model;


import lombok.*;

import javax.persistence.*;
import java.util.*;


@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Getter
@Setter
@Entity
@Table(name = "USERS")
/**
 * DataBase Entity for persisting User
 */
public class UserEntity {

    @Id()
    @GeneratedValue
    private int id;

    @Column
    String name;

    @Column(name = "user_name", unique = true)
    String userName;

    @Column
    int age;


    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
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
    public void removeSurvey() {
        Iterator<SurveyEntity> iterator = performedSurveys.iterator();
        while (iterator.hasNext()){
            SurveyEntity surveyEntity = iterator.next();
            iterator.remove();
            surveyEntity.user =null;
        }

        this.getPerformedSurveys().clear();
    }

    /**
     * removes the survey from the user for further deletion of the survey
     *
     * @param survey to be removed from this user
     */
    public void removeSurvey(SurveyEntity survey) {
        this.getPerformedSurveys().removeIf(surveyEntity -> surveyEntity.getId() == survey.getId());
    }


    public void addSurvey(SurveyEntity survey) {
        performedSurveys.add(survey);

        if (survey.getUser() != this) {
            survey.setUser(this);
        }
    }


    /*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return id == that.id && age == that.age && Objects.equals(name, that.name) && Objects.equals(userName, that.userName) && Objects.equals(performedSurveys, that.performedSurveys);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, userName, age, performedSurveys);
    }
    */
    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userName='" + userName + '\'' +
                ", age=" + age +
                ", performedSurveys=" + performedSurveys +
                '}';
    }
}
