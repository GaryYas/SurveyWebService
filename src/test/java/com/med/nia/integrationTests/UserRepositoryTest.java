package com.med.nia.integrationTests;

import com.med.nia.model.SurveyEntity;
import com.med.nia.model.UserEntity;
import com.med.nia.repository.SurveyRepository;
import com.med.nia.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SurveyRepository surveyRepository;


    @AfterEach
    public void after() {
        userRepository.deleteAll();
    }

    @Test
    public void userCreationAndDeletionTest() {
        UserEntity user1 = userRepository.save(new UserEntity("John Doe", "Johny", 23));
        UserEntity user2 = userRepository.save(new UserEntity("Jolly Wane", "Jolle", 25));

        assertTrue(userRepository.findById(user1.getId()).isPresent());
        assertTrue(userRepository.findById(user2.getId()).isPresent());

        userRepository.deleteById(user1.getId());
        assertTrue(userRepository.findById(user1.getId()).isEmpty());
        userRepository.deleteById(user2.getId());
        assertTrue(userRepository.findById(user2.getId()).isEmpty());

        assertEquals(0, userRepository.findAll().size());

    }

    @Test
    public void verifySurveysRemovedAfterUserDeletionTest() {

        UserEntity user1 = userRepository.save(new UserEntity("John Doe", "Johny", 23));

        SurveyEntity survey1 = surveyRepository.save(new SurveyEntity(2, 1, user1, new Date()));
        SurveyEntity survey2 = surveyRepository.save(new SurveyEntity(1, 1, user1, new Date()));

        survey1.setUser(user1);
        survey2.setUser(user1);

        assertTrue(userRepository.findById(user1.getId()).isPresent());
        assertEquals(2, userRepository.findById(user1.getId()).get().getPerformedSurveys().size());
        userRepository.deleteById(user1.getId());
        assertTrue(surveyRepository.findById(user1.getId()).isEmpty());
    }


}
