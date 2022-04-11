package com.med.nia.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Dto object for survey information
 */
public class SurveyDto {
    int sleepRate;
    int skinCondition;
    int userId;
    int serviceId;

    @SuppressWarnings("all")
    SurveyDto(final int sleepRate, final int skinCondition, final int userId, final int serviceId) {
        this.sleepRate = sleepRate;
        this.skinCondition = skinCondition;
        this.userId = userId;
        this.serviceId = serviceId;
    }


    @SuppressWarnings("all")
    public static class SurveyDtoBuilder {
        @SuppressWarnings("all")
        private int sleepRate;
        @SuppressWarnings("all")
        private int skinCondition;
        @SuppressWarnings("all")
        private int userId;
        @SuppressWarnings("all")
        private int serviceId;

        @SuppressWarnings("all")
        SurveyDtoBuilder() {
        }

        @SuppressWarnings("all")
        public SurveyDto.SurveyDtoBuilder sleepRate(final int sleepRate) {
            this.sleepRate = sleepRate;
            return this;
        }

        @SuppressWarnings("all")
        public SurveyDto.SurveyDtoBuilder skinCondition(final int skinCondition) {
            this.skinCondition = skinCondition;
            return this;
        }

        @SuppressWarnings("all")
        public SurveyDto.SurveyDtoBuilder userId(final int userId) {
            this.userId = userId;
            return this;
        }

        @SuppressWarnings("all")
        public SurveyDto.SurveyDtoBuilder serviceId(final int serviceId) {
            this.serviceId = serviceId;
            return this;
        }

        @SuppressWarnings("all")
        public SurveyDto build() {
            return new SurveyDto(this.sleepRate, this.skinCondition, this.userId, this.serviceId);
        }

        @Override
        @SuppressWarnings("all")
        public String toString() {
            return "SurveyDto.SurveyDtoBuilder(sleepRate=" + this.sleepRate + ", skinCondition=" + this.skinCondition + ", userId=" + this.userId + ", serviceId=" + this.serviceId + ")";
        }
    }

    @SuppressWarnings("all")
    public static SurveyDto.SurveyDtoBuilder builder() {
        return new SurveyDto.SurveyDtoBuilder();
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
    public int getUserId() {
        return this.userId;
    }

    @SuppressWarnings("all")
    public int getServiceId() {
        return this.serviceId;
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
    public void setUserId(final int userId) {
        this.userId = userId;
    }

    @SuppressWarnings("all")
    public void setServiceId(final int serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    @SuppressWarnings("all")
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SurveyDto)) return false;
        final SurveyDto other = (SurveyDto) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getSleepRate() != other.getSleepRate()) return false;
        if (this.getSkinCondition() != other.getSkinCondition()) return false;
        if (this.getUserId() != other.getUserId()) return false;
        if (this.getServiceId() != other.getServiceId()) return false;
        return true;
    }

    @SuppressWarnings("all")
    protected boolean canEqual(final Object other) {
        return other instanceof SurveyDto;
    }

    @Override
    @SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getSleepRate();
        result = result * PRIME + this.getSkinCondition();
        result = result * PRIME + this.getUserId();
        result = result * PRIME + this.getServiceId();
        return result;
    }

    @Override
    @SuppressWarnings("all")
    public String toString() {
        return "SurveyDto(sleepRate=" + this.getSleepRate() + ", skinCondition=" + this.getSkinCondition() + ", userId=" + this.getUserId() + ", serviceId=" + this.getServiceId() + ")";
    }
}
