package com.med.nia;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.med.nia.api.SurveyController;
import com.med.nia.api.UserController;
import com.med.nia.dto.SurveyDto;
import com.med.nia.service.SurveyService;
import lombok.SneakyThrows;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SurveyController.class)
public class SurveyControllerTest {

    @MockBean
    SurveyService surveyService;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void getAllUsersTest() throws Exception {
        List<SurveyDto> surveyDtos = List.of(SurveyDto.builder().sleepRate(1).userId(1).skinCondition(1).build(),
                SurveyDto.builder().sleepRate(3).skinCondition(2).userId(2).build());

        when(surveyService.getAllSurveys()).thenReturn(surveyDtos);
        mockMvc.perform(get("/survey/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].sleepRate", Matchers.is(1)))
                .andExpect(jsonPath("$[0].skinCondition", Matchers.is(1)))
                .andExpect(jsonPath("$[0].userId", Matchers.is(1)))
                .andExpect(jsonPath("$[1].sleepRate", Matchers.is(3)))
                .andExpect(jsonPath("$[1].skinCondition", Matchers.is(2)))
                .andExpect(jsonPath("$[1].userId", Matchers.is(2)));

    }

    @SneakyThrows
    @Test
    public void saveUserTest() {
        SurveyDto surveyDto = SurveyDto.builder().sleepRate(3).skinCondition(2).userId(2).build();
        when(surveyService.saveSurvey(any(SurveyDto.class))).thenReturn(surveyDto);

        mockMvc.perform(post("/survey")
                .content(mapper.writeValueAsString(surveyDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sleepRate").value(3))
                .andExpect(jsonPath("$.skinCondition").value(2))
                .andExpect(jsonPath("$.userId", Matchers.is(2)));

    }

    @SneakyThrows
    @Test
    public void deleteUserTest() {
        int surveyIdToDelete = 1;
        when(surveyService.deleteSurveyById(anyInt())).thenReturn(surveyIdToDelete);

        mockMvc.perform(delete("/survey")
                .param("surveyId","1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(1));
    }
}
