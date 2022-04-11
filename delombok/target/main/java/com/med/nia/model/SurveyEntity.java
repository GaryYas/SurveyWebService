package com.med.nia.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import javax.persistence.*;
import javax.validation.constraints.Max;
import java.util.Date;
import java.util.Objects;

/**
 * DataBase Entity for persisting survey
 */
@Entity
@Table(name = "Surveys")
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
    public void deleteEntity() {
        this.user.removeSurveys(this);
        this.user = null;
    }

    public void setUser(UserEntity user) {
        this.user = user;
        if (!user.getPerformedSurveys().contains(this)) user.addSurvey(this);
    }

    @SuppressWarnings("all")
    public int getId() {
        return this.id;
    }

    @SuppressWarnings("all")
    public int getSleepRate() {
        return this.sleepRate;
    }

    @SuppressWarnings("all")
    public int getSkinCondition() {
        return this.skinCondition;
    }

    @SuppressWarnings("all")
    public UserEntity getUser() {
        return this.user;
    }

    @SuppressWarnings("all")
    public Date getCreatedAt() {
        return this.createdAt;
    }

    @SuppressWarnings("all")
    public void setId(final int id) {
        this.id = id;
    }

    @SuppressWarnings("all")
    public void setSleepRate(final int sleepRate) {
        this.sleepRate = sleepRate;
    }

    @SuppressWarnings("all")
    public void setSkinCondition(final int skinCondition) {
        this.skinCondition = skinCondition;
    }

    @SuppressWarnings("all")
    public void setCreatedAt(final Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    @SuppressWarnings("all")
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SurveyEntity)) return false;
        final SurveyEntity other = (SurveyEntity) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getId() != other.getId()) return false;
        if (this.getSleepRate() != other.getSleepRate()) return false;
        if (this.getSkinCondition() != other.getSkinCondition()) return false;
        final Object this$user = this.getUser();
        final Object other$user = other.getUser();
        if (this$user == null ? other$user != null : !this$user.equals(other$user)) return false;
        final Object this$createdAt = this.getCreatedAt();
        final Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        return true;
    }

    @SuppressWarnings("all")
    protected boolean canEqual(final Object other) {
        return other instanceof SurveyEntity;
    }

    @Override
    @SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getId();
        result = result * PRIME + this.getSleepRate();
        result = result * PRIME + this.getSkinCondition();
        final Object $user = this.getUser();
        result = result * PRIME + ($user == null ? 43 : $user.hashCode());
        final Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        return result;
    }

    @Override
    @SuppressWarnings("all")
    public String toString() {
        return "SurveyEntity(id=" + this.getId() + ", sleepRate=" + this.getSleepRate() + ", skinCondition=" + this.getSkinCondition() + ", user=" + this.getUser() + ", createdAt=" + this.getCreatedAt() + ")";
    }
}
