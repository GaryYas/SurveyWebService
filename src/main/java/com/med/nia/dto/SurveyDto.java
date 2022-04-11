package com.med.nia.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


@Data
@Builder
/**
 * Dto object for survey information
 */
public class SurveyDto {

    int sleepRate;
    int skinCondition;
    int userId;
    int serviceId;

}
