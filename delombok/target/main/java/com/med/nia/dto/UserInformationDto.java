package com.med.nia.dto;

/**
 * Dto request information for user
 */
public class UserInformationDto {
    String name;
    int age;
    String userName;

    @SuppressWarnings("all")
    UserInformationDto(final String name, final int age, final String userName) {
        this.name = name;
        this.age = age;
        this.userName = userName;
    }


    @SuppressWarnings("all")
    public static class UserInformationDtoBuilder {
        @SuppressWarnings("all")
        private String name;
        @SuppressWarnings("all")
        private int age;
        @SuppressWarnings("all")
        private String userName;

        @SuppressWarnings("all")
        UserInformationDtoBuilder() {
        }

        @SuppressWarnings("all")
        public UserInformationDto.UserInformationDtoBuilder name(final String name) {
            this.name = name;
            return this;
        }

        @SuppressWarnings("all")
        public UserInformationDto.UserInformationDtoBuilder age(final int age) {
            this.age = age;
            return this;
        }

        @SuppressWarnings("all")
        public UserInformationDto.UserInformationDtoBuilder userName(final String userName) {
            this.userName = userName;
            return this;
        }

        @SuppressWarnings("all")
        public UserInformationDto build() {
            return new UserInformationDto(this.name, this.age, this.userName);
        }

        @Override
        @SuppressWarnings("all")
        public String toString() {
            return "UserInformationDto.UserInformationDtoBuilder(name=" + this.name + ", age=" + this.age + ", userName=" + this.userName + ")";
        }
    }

    @SuppressWarnings("all")
    public static UserInformationDto.UserInformationDtoBuilder builder() {
        return new UserInformationDto.UserInformationDtoBuilder();
    }

    @SuppressWarnings("all")
    public String getName() {
        return this.name;
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
    public void setName(final String name) {
        this.name = name;
    }

    @SuppressWarnings("all")
    public void setAge(final int age) {
        this.age = age;
    }

    @SuppressWarnings("all")
    public void setUserName(final String userName) {
        this.userName = userName;
    }

    @Override
    @SuppressWarnings("all")
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UserInformationDto)) return false;
        final UserInformationDto other = (UserInformationDto) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getAge() != other.getAge()) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$userName = this.getUserName();
        final Object other$userName = other.getUserName();
        if (this$userName == null ? other$userName != null : !this$userName.equals(other$userName)) return false;
        return true;
    }

    @SuppressWarnings("all")
    protected boolean canEqual(final Object other) {
        return other instanceof UserInformationDto;
    }

    @Override
    @SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getAge();
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $userName = this.getUserName();
        result = result * PRIME + ($userName == null ? 43 : $userName.hashCode());
        return result;
    }

    @Override
    @SuppressWarnings("all")
    public String toString() {
        return "UserInformationDto(name=" + this.getName() + ", age=" + this.getAge() + ", userName=" + this.getUserName() + ")";
    }
}
