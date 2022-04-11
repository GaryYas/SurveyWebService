package com.med.nia.integrationTests;

import com.med.nia.model.SurveyEntity;
import com.med.nia.model.UserEntity;
import com.med.nia.repository.SurveyRepository;
import com.med.nia.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class SurveyRepositoryTest {

    @Autowired
    SurveyRepository surveyRepository;

    @Autowired
    UserRepository userRepository;
    UserEntity user;
    UserEntity user2;

    @BeforeEach
    public void before(){
         user = userRepository.save(new UserEntity("Paul","Bad",20));
         user2 = userRepository.save(new UserEntity("Tim","Evans",20));
    }


    @AfterEach
    public void after(){
        userRepository.deleteAll();
    }

    @Test
    public void CreationAndDeletionTest(){

        SurveyEntity surveyEntity = surveyRepository.save(new SurveyEntity(1, 1, user, new Date()));
        SurveyEntity surveyEntity2 = surveyRepository.save(new SurveyEntity(1, 1, user2, new Date()));

        surveyEntity.setUser(user);
        surveyEntity2.setUser(user2);

        assertTrue(userRepository.findById(user.getId()).isPresent());
        assertTrue(surveyRepository.findById(surveyEntity.getId()).isPresent());
        assertEquals( 2,surveyRepository.findAll().size());
        assertEquals(surveyRepository.findById(surveyEntity.getId()).get().getUser(), user);

       assertTrue(userRepository.findById(user.getId()).get().getPerformedSurveys().contains(surveyEntity));


       //check orphan removal
        userRepository.deleteById(user.getId());
        assertTrue(surveyRepository.findById(surveyEntity.getId()).isEmpty());

    }


    @Test
    public void deleteSurveys(){

        SurveyEntity surveyEntity = surveyRepository.save(new SurveyEntity(1, 1, user, new Date()));
        SurveyEntity surveyEntity2 = surveyRepository.save(new SurveyEntity(1, 1, user2, new Date()));
        SurveyEntity surveyEntity3 = surveyRepository.save(new SurveyEntity(1, 1, user, new Date()));
        SurveyEntity surveyEntity4 = surveyRepository.save(new SurveyEntity(1, 1, user2, new Date()));


        surveyEntity.setUser(user);
        surveyEntity2.setUser(user2);
        surveyEntity3.setUser(user);
        surveyEntity4.setUser(user2);

        surveyRepository.getById(surveyEntity3.getId()).deleteEntity();
        surveyRepository.deleteById(surveyEntity3.getId());
        assertEquals(3, surveyRepository.findAll().size());

        List<Integer> surveysOfUser2Ids = user2.getPerformedSurveys().stream().map(SurveyEntity::getId).collect(Collectors.toList());
        user2.removeSurvey();
        surveyRepository.deleteSurveysWithIds(surveysOfUser2Ids);
        assertEquals(1, surveyRepository.findAll().size());

        List<SurveyEntity> allSurveys = surveyRepository.findAll();
        allSurveys.forEach(SurveyEntity::deleteEntity);
        surveyRepository.deleteAll();
        assertEquals(0, surveyRepository.findAll().size());

    }
}
