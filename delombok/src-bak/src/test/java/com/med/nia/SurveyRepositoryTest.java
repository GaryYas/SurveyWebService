package com.med.nia;

import com.med.nia.model.SurveyEntity;
import com.med.nia.model.UserEntity;
import com.med.nia.repository.SurveyRepository;
import com.med.nia.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class SurveyRepositoryTest {

    @Autowired
    SurveyRepository surveyRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    UserRepository userRepository;

    @Test
    public void CreationAndDeletionTest(){
        UserEntity user = testEntityManager.persist(new UserEntity("sa","sa",1));
        surveyRepository.save(new SurveyEntity(1,1,user,new Date()));
        surveyRepository.findAll();


    }


    @Test
    public void verifySurveysRemovedAfterUserDeletionTest(){
        UserEntity user =  testEntityManager.persist(new UserEntity("hah","fafa",1));
        assertNotNull(user);

        surveyRepository.save(new SurveyEntity(1,1,user, new Date()));
        surveyRepository.save(new SurveyEntity(2,1,user, new Date()));

        testEntityManager.clear();
        assertEquals(2, userRepository.findById(1).get().getPerformedSurveys().size());
        userRepository.deleteById(1);
        assertTrue(surveyRepository.findById(1).isEmpty());
    }
}
