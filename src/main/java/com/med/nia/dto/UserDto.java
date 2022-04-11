package com.med.nia.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
/**
 * Dto response for user
 */
public class UserDto {

    String name;
    int id;
    int age;
    String userName;
    List<SurveyDto> performedSurveys;

}
