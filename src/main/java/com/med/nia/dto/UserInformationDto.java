package com.med.nia.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
/**
 * Dto request information for user
 */
public class UserInformationDto {

    String name;
    int age;
    String userName;
}
