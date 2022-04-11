package com.med.nia.service;

import com.med.nia.dto.SurveyDto;
import com.med.nia.model.SurveyEntity;
import com.med.nia.model.UserEntity;
import com.med.nia.repository.SurveyRepository;
import com.med.nia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static com.med.nia.service.ServiceUtils.*;

@Service
public class SurveyService {
    @SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SurveyService.class);
    @Autowired
    SurveyRepository surveyRepository;
    @Autowired
    UserRepository userRepository;

    /**
     * Persists new surveys to database and verifies the constraint
     *
     * @param surveyDto survey information for persistence
     * @return 200 for successful persistence of the survey
     * 404 for not existed user
     * 500 for internal server error
     */
    public ResponseEntity<SurveyDto> saveSurvey(SurveyDto surveyDto) {
        if (!verifySurveySleepRateConstraint(surveyDto) || !verifySurveySkinConditionConstraint(surveyDto)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "sleep and skin condition values should be between 0 and 10");
        try {
            Optional<UserEntity> user = userRepository.findById(surveyDto.getUserId());
            if (user.isEmpty()) return new ResponseEntity<>(surveyDto, HttpStatus.NOT_FOUND);
            SurveyEntity survey = surveyRepository.save(new SurveyEntity(surveyDto.getSleepRate(), surveyDto.getSkinCondition(), user.get(), new Date()));
            survey.setUser(user.get());
            surveyDto.setServiceId(survey.getId());
            return new ResponseEntity<>(surveyDto, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("retrieved error while saving", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves all the surveys that belongs to user
     *
     * @param userId that surveys belongs to
     * @return all surveys by user id(200)
     * (400) if user id not found
     * (500) for internal server error
     */
    public ResponseEntity<List<SurveyDto>> getAllSurveysByUser(int userId) {
        Optional<UserEntity> user = userRepository.findById(userId);
        try {
            if (user.isEmpty()) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(transFormSurveyEntityToDto(user.get().getPerformedSurveys()), HttpStatus.OK);
        } catch (Exception e) {
            log.error("retrieved error while saving", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves survey by its id
     *
     * @param id of the survey to be returned
     * @return return survey (200)
     * (400) survey not found error
     * (500) internal survey error
     */
    public ResponseEntity<SurveyDto> getSurveyById(int id) {
        try {
            Optional<SurveyEntity> surveyEntity = surveyRepository.findById(id);
            if (surveyEntity.isEmpty()) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(transSurveyEntityToDto(surveyEntity.get()), HttpStatus.OK);
        } catch (Exception e) {
            log.error("retrieved error while saving", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * retrieves all persisted surveys
     *
     * @return list of all surveys (200)
     * (400) for internal server error
     */
    public ResponseEntity<List<SurveyDto>> getAllSurveys() {
        try {
            List<SurveyEntity> allSurveys = surveyRepository.findAll();
            return new ResponseEntity<>(transFormSurveyEntityToDto(allSurveys), HttpStatus.OK);
        } catch (Exception e) {
            log.error("retrieved error while saving", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param id of the survey to be deleted
     * @return id of the deleted survey(200)
     * (500) for internal error
     */
    public ResponseEntity<Integer> deleteSurveyById(int id) {
        try {
            if (surveyRepository.existsById(id)) {
                surveyRepository.getById(id).deleteEntity();
                surveyRepository.deleteById(id);
            }
            return new ResponseEntity<>(id, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            log.error("retrieved error while saving", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * deletes all the surveys that belong to user
     *
     * @param userId of the service
     * @return list of deleted ids (200)
     * (400) in case user not found
     */
    @Transactional
    public ResponseEntity<List<Integer>> deleteAllSurveysBelongToUser(int userId) {
        try {
            Optional<UserEntity> user = userRepository.findById(userId);
            if (user.isEmpty()) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            List<Integer> surveysIdsToDelete = user.get().getPerformedSurveys().stream().map(SurveyEntity::getId).collect(Collectors.toList());
            user.get().removeSurveys();
            surveyRepository.deleteSurveysWithIds(surveysIdsToDelete);
            return new ResponseEntity<>(surveysIdsToDelete, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            log.error("retrieved error while saving", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes all the surveys
     *
     * @return
     */
    public ResponseEntity<Void> deleteAllSurveys() {
        try {
            List<SurveyEntity> allSurveys = surveyRepository.findAll();
            allSurveys.forEach(SurveyEntity::deleteEntity);
            surveyRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            log.error("retrieved error while saving", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
