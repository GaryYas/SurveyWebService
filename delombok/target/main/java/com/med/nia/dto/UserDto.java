package com.med.nia.dto;

import java.util.List;

/**
 * Dto response for user
 */
public class UserDto {
    String name;
    int id;
    int age;
    String userName;
    List<SurveyDto> performedSurveys;

    @SuppressWarnings("all")
    UserDto(final String name, final int id, final int age, final String userName, final List<SurveyDto> performedSurveys) {
        this.name = name;
        this.id = id;
        this.age = age;
        this.userName = userName;
        this.performedSurveys = performedSurveys;
    }


    @SuppressWarnings("all")
    public static class UserDtoBuilder {
        @SuppressWarnings("all")
        private String name;
        @SuppressWarnings("all")
        private int id;
        @SuppressWarnings("all")
        private int age;
        @SuppressWarnings("all")
        private String userName;
        @SuppressWarnings("all")
        private List<SurveyDto> performedSurveys;

        @SuppressWarnings("all")
        UserDtoBuilder() {
        }

        @SuppressWarnings("all")
        public UserDto.UserDtoBuilder name(final String name) {
            this.name = name;
            return this;
        }

        @SuppressWarnings("all")
        public UserDto.UserDtoBuilder id(final int id) {
            this.id = id;
            return this;
        }

        @SuppressWarnings("all")
        public UserDto.UserDtoBuilder age(final int age) {
            this.age = age;
            return this;
        }

        @SuppressWarnings("all")
        public UserDto.UserDtoBuilder userName(final String userName) {
            this.userName = userName;
            return this;
        }

        @SuppressWarnings("all")
        public UserDto.UserDtoBuilder performedSurveys(final List<SurveyDto> performedSurveys) {
            this.performedSurveys = performedSurveys;
            return this;
        }

        @SuppressWarnings("all")
        public UserDto build() {
            return new UserDto(this.name, this.id, this.age, this.userName, this.performedSurveys);
        }

        @Override
        @SuppressWarnings("all")
        public String toString() {
            return "UserDto.UserDtoBuilder(name=" + this.name + ", id=" + this.id + ", age=" + this.age + ", userName=" + this.userName + ", performedSurveys=" + this.performedSurveys + ")";
        }
    }

    @SuppressWarnings("all")
    public static UserDto.UserDtoBuilder builder() {
        return new UserDto.UserDtoBuilder();
    }

    @SuppressWarnings("all")
    public String getName() {
        return this.name;
    }

    @SuppressWarnings("all")
    public int getId() {
        return this.id;
    }

    @SuppressWarnings("all")
    public int getAge() {
        return this.age;
    }

    @SuppressWarnings("all")
    public String getUserName() {
        return this.userName;
    }

    @SuppressWarnings("all")
    public List<SurveyDto> getPerformedSurveys() {
        return this.performedSurveys;
    }

    @SuppressWarnings("all")
    public void setName(final String name) {
        this.name = name;
    }

    @SuppressWarnings("all")
    public void setId(final int id) {
        this.id = id;
    }

    @SuppressWarnings("all")
    public void setAge(final int age) {
        this.age = age;
    }

    @SuppressWarnings("all")
    public void setUserName(final String userName) {
        this.userName = userName;
    }

    @SuppressWarnings("all")
    public void setPerformedSurveys(final List<SurveyDto> performedSurveys) {
        this.performedSurveys = performedSurveys;
    }

    @Override
    @SuppressWarnings("all")
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UserDto)) return false;
        final UserDto other = (UserDto) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getId() != other.getId()) return false;
        if (this.getAge() != other.getAge()) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$userName = this.getUserName();
        final Object other$userName = other.getUserName();
        if (this$userName == null ? other$userName != null : !this$userName.equals(other$userName)) return false;
        final Object this$performedSurveys = this.getPerformedSurveys();
        final Object other$performedSurveys = other.getPerformedSurveys();
        if (this$performedSurveys == null ? other$performedSurveys != null : !this$performedSurveys.equals(other$performedSurveys)) return false;
        return true;
    }

    @SuppressWarnings("all")
    protected boolean canEqual(final Object other) {
        return other instanceof UserDto;
    }

    @Override
    @SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getId();
        result = result * PRIME + this.getAge();
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $userName = this.getUserName();
        result = result * PRIME + ($userName == null ? 43 : $userName.hashCode());
        final Object $performedSurveys = this.getPerformedSurveys();
        result = result * PRIME + ($performedSurveys == null ? 43 : $performedSurveys.hashCode());
        return result;
    }

    @Override
    @SuppressWarnings("all")
    public String toString() {
        return "UserDto(name=" + this.getName() + ", id=" + this.getId() + ", age=" + this.getAge() + ", userName=" + this.getUserName() + ", performedSurveys=" + this.getPerformedSurveys() + ")";
    }
}
