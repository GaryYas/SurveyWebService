package com.med.nia.service;

import com.med.nia.dto.SurveyDto;
import com.med.nia.dto.UserDto;
import com.med.nia.model.SurveyEntity;
import com.med.nia.model.UserEntity;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceUtils {

    /**
     * Transforms Database survey Entity object To survey DTO
     * @param surveyEntity to be transformed to survey DTO
     * @return new transformed Survey DTO
     */
    public static SurveyDto transSurveyEntityToDto(SurveyEntity surveyEntity) {
        return SurveyDto.builder().userId(surveyEntity.getUser().getId()).skinCondition(surveyEntity.getSkinCondition()).
                sleepRate(surveyEntity.getSleepRate()).serviceId(surveyEntity.getId())
                .build();
    }

    /**
     * Transforms Database survey Entities  object To surveys DTO
     * @param performedSurveys List of survey entities to be transformed to survey DTO
     * @return new transformed Survey DTO list
     */
    public static List<SurveyDto> transFormSurveyEntityToDto(Collection<SurveyEntity> performedSurveys) {

        return performedSurveys.stream().map(ServiceUtils::transSurveyEntityToDto).collect(Collectors.toList());
    }

    /**
     * Transforms Database User Entity object To user DTO
     * @param userEntity to be transformed to user DTO
     * @return new transformed User DTO
     */
    public static UserDto transformFromUserEntityToDto(UserEntity userEntity) {

        return UserDto.builder().name(userEntity.getName()).age(userEntity.getAge()).userName(userEntity.getUserName()).
                id(userEntity.getId()).
                performedSurveys(transFormSurveyEntityToDto(userEntity.getPerformedSurveys())).build();
    }

    /**
     * Transforms Database users Entities  object To users DTO
     * @param users List of users entities to be transformed to users DTO
     * @return new transformed users DTO list
     */
    public static List<UserDto> transformFromUserEntityToDto(List<UserEntity> users) {
        return users.stream().map(ServiceUtils::transformFromUserEntityToDto).collect(Collectors.toList());
    }

    /**
     * @param surveyDto to check the constraint for sleep rate
     * @return true if sleep rate higher or equals than 0 and lower or equals than 10
     */
    public static boolean verifySurveySleepRateConstraint(SurveyDto surveyDto) {
        return surveyDto.getSleepRate() <= 10 && surveyDto.getSleepRate() >= 0;
    }

    /**
     * @param surveyDto to check the constraint for sleep rate
     * @return true if skin condition higher or equals than 0 and lower or equals than 10
     */
    public static boolean verifySurveySkinConditionConstraint(SurveyDto surveyDto) {
        return surveyDto.getSkinCondition() <= 10 && surveyDto.getSkinCondition() >= 0;
    }
}
