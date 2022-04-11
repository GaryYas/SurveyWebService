package com.med.nia.dto;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.Date;


@Data
@Builder
/**
 * Dto object for survey information
 */
public class SurveyDto {

    int sleepRate;
    int skinCondition;
    int userId;
    @Schema(required = false, hidden = true)
    int serviceId;
    @Schema(required = false, hidden = true)
    Date date;

}
