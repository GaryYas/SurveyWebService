package com.med.nia;

import com.med.nia.dto.SurveyDto;
import com.med.nia.exception.BadSurveyParametersException;
import com.med.nia.exception.ResourceNotFoundException;
import com.med.nia.model.SurveyEntity;
import com.med.nia.model.UserEntity;
import com.med.nia.repository.SurveyRepository;
import com.med.nia.repository.UserRepository;
import com.med.nia.service.SurveyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class SurveyServiceTest {

    @InjectMocks
    SurveyService surveyService;

    @Mock
    SurveyRepository surveyRepository;

    @Mock
    UserRepository userRepository;

    @Test
    public void addSurvey() {

        SurveyDto surveyDto = SurveyDto.builder().sleepRate(1).skinCondition(2).userId(1).build();
        UserEntity userEntity = new UserEntity("Deni", "Avdjia", 34);
        SurveyEntity surveyEntity = new SurveyEntity(1, 2, userEntity, new Date());

        when(userRepository.findById(eq(surveyDto.getUserId()))).thenReturn(Optional.of(userEntity));
        when(surveyRepository.save(any(SurveyEntity.class))).thenReturn(surveyEntity);

        SurveyDto fetchedSurvey = surveyService.saveSurvey(surveyDto);

        assertEquals(fetchedSurvey.getSkinCondition(), surveyDto.getSkinCondition());
        assertEquals(fetchedSurvey.getSleepRate(), surveyDto.getSleepRate());
    }

    @Test
    public void addSurveyWithNotExistedUser() {
        int userId = 1;
        SurveyDto surveyDto = SurveyDto.builder().sleepRate(1).skinCondition(2).userId(1).build();
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> surveyService.saveSurvey(surveyDto));
        String message = exception.getMessage();
        assertTrue(message.contains("user with id " + userId + " does not exist"));
    }

    @Test
    public void addSurveyWithIncorrectSleepAndSkinParameters() {
        SurveyDto surveyDto = SurveyDto.builder().sleepRate(22).skinCondition(11).userId(1).build();
        BadSurveyParametersException exception = assertThrows(BadSurveyParametersException.class, () -> surveyService.saveSurvey(surveyDto));
        String message = exception.getMessage();
        assertTrue(message.contains("sleep and skin condition values should be between 0 and 10"));
    }

    @Test
    public void getAllSurveysByUser() {
        int userId = 1;
        UserEntity userEntity = new UserEntity("Deni", "Avdjia", 34);
        SurveyEntity surveyEntity = new SurveyEntity(1, 2, userEntity, new Date());
        when(surveyRepository.findById(eq(userId))).thenReturn(Optional.of(surveyEntity));
        SurveyDto fetchedService = surveyService.getSurveyById(userId);
        assertEquals(fetchedService.getSleepRate(), surveyEntity.getSleepRate());
        assertEquals(fetchedService.getSkinCondition(), surveyEntity.getSkinCondition());
    }

    @Test
    public void getAllSurveys() {
        UserEntity userEntity = new UserEntity("Deni", "Avdjia", 34);
        SurveyEntity surveyEntity = new SurveyEntity(1, 2, userEntity, new Date());
        UserEntity userEntity2 = new UserEntity("Shimi", "oren", 34);
        SurveyEntity surveyEntity2 = new SurveyEntity(1, 2, userEntity2, new Date());
        List<SurveyEntity> surveyEntities = List.of(surveyEntity, surveyEntity2);

        when(surveyRepository.findAll()).thenReturn(surveyEntities);
        List<SurveyDto> allSurveys = surveyService.getAllSurveys();

        assertEquals(allSurveys.size(), surveyEntities.size());
        verify(surveyRepository, times(1)).findAll();
    }

    @Test
    public void deleteSurveyById() {
        int surveyId = 1;
        UserEntity userEntity = new UserEntity("Deni", "Avdjia", 34);
        SurveyEntity surveyEntity = new SurveyEntity(1, 2, userEntity, new Date());
        when(surveyRepository.existsById(eq(surveyId))).thenReturn(true);
        when(surveyRepository.getById(eq(surveyId))).thenReturn(surveyEntity);
        Mockito.doNothing().when(surveyRepository).deleteById(surveyId);
        int deletedId = surveyService.deleteSurveyById(surveyId);
        assertEquals(deletedId, surveyId);
    }

    @Test
    public void deleteAllSurveysBelongToUser() {
        int userId = 1;
        int surveyId1 = 2;
        int surveyId2 = 3;
        UserEntity userEntity = new UserEntity("Deni", "Avdjia", 34);
        SurveyEntity surveyEntity = new SurveyEntity(1, 2, userEntity, new Date());
        surveyEntity.setId(surveyId1);
        SurveyEntity surveyEntity2 = new SurveyEntity(1, 2, userEntity, new Date());
        surveyEntity2.setId(surveyId2);
        userEntity.addSurvey(surveyEntity);
        userEntity.addSurvey(surveyEntity2);


        when(userRepository.findById(eq(userId))).thenReturn(Optional.of(userEntity));
        ArrayList<Integer> serviceIdToDelete = new ArrayList<>();
        serviceIdToDelete.add(surveyId1);
        serviceIdToDelete.add(surveyId2);
        Mockito.doNothing().when(surveyRepository).deleteSurveysWithIds(any());
        Set<Integer> deletedIds = new HashSet<>(surveyService.deleteAllSurveysBelongToUser(userId));
        assertTrue(deletedIds.contains(surveyId1));
        assertTrue(deletedIds.contains(surveyId2));

    }


}
