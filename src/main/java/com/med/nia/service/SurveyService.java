package com.med.nia.service;

import com.med.nia.dto.SurveyDto;
import com.med.nia.exception.BadSurveyParametersException;
import com.med.nia.exception.ResourceNotFoundException;
import com.med.nia.model.SurveyEntity;
import com.med.nia.model.UserEntity;
import com.med.nia.repository.SurveyRepository;
import com.med.nia.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.med.nia.service.ServiceUtils.*;

@Service
@Slf4j
public class SurveyService {

    @Autowired
    SurveyRepository surveyRepository;

    @Autowired
    UserRepository userRepository;

    /**
     * Persists new surveys to database and verifies the constraint
     *
     * @param surveyDto survey information for persistence
     * @return persisted survey dto
     * throw BadSurveyParametersException if sleep rate or skin condition are not between 0 and 10
     * throw ResourceNotFoundException if there is no such user
     */
    public SurveyDto saveSurvey(SurveyDto surveyDto) {

        if (!verifySurveySleepRateConstraint(surveyDto) || !verifySurveySkinConditionConstraint(surveyDto))
            throw new BadSurveyParametersException("sleep and skin condition values should be between 0 and 10");

        UserEntity user = userRepository.findById(surveyDto.getUserId()).orElseThrow(() ->
                new ResourceNotFoundException("user with id " + surveyDto.getUserId() + " does not exist"));

        SurveyEntity survey = surveyRepository.save(new SurveyEntity(surveyDto.getSleepRate(), surveyDto.getSkinCondition(), user, new Date()));
        survey.setUser(user);
        surveyDto.setServiceId(survey.getId());

        return surveyDto;

    }

    /**
     * Retrieves all the surveys that belongs to user
     *
     * @param userId that surveys belongs to
     * @return all surveys by user id
     * throw ResourceNotFoundException if there is no such user
     */
    public List<SurveyDto> getAllSurveysByUser(int userId) {

        UserEntity user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("user with id " + userId + " does not exist"));
        ;
        return transFormSurveyEntityToDto(user.getPerformedSurveys());
    }

    /**
     * Retrieves survey by its id
     *
     * @param id of the survey to be returned
     * @return return survey
     * throw ResourceNotFoundException if there is no such survey
     */
    public SurveyDto getSurveyById(int id) {
        SurveyEntity surveyEntity = surveyRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("user with id" + id + "does not exist"));
        return transSurveyEntityToDto(surveyEntity);
    }


    /**
     * retrieves all persisted surveys
     *
     * @return list of all surveys
     */
    public List<SurveyDto> getAllSurveys() {
        List<SurveyEntity> allSurveys = surveyRepository.findAll();
        return transFormSurveyEntityToDto(allSurveys);

    }

    /**
     * @param id of the survey to be deleted
     * @return id of the deleted survey
     * throw ResourceNotFoundException if there is no such survey
     */
    public int deleteSurveyById(int id) {
        if (surveyRepository.existsById(id)) {
            surveyRepository.getById(id).deleteEntity();
            surveyRepository.deleteById(id);
            return id;

        }
        throw new ResourceNotFoundException("survey with id " + id + " does not exist");
    }

    /**
     * deletes all the surveys that belong to user
     *
     * @param userId of the service
     * @return list of deleted ids
     * throw ResourceNotFoundException if there is no such user
     */
    @Transactional
    public List<Integer> deleteAllSurveysBelongToUser(int userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("user with id" + userId + "does not exist"));

        List<Integer> surveysIdsToDelete = user.getPerformedSurveys().stream().map(SurveyEntity::getId).collect(Collectors.toList());
        user.removeSurvey();
        surveyRepository.deleteSurveysWithIds(surveysIdsToDelete);
        return surveysIdsToDelete;
    }

    /**
     * Deletes all the surveys
     *
     * @return
     */
    public void deleteAllSurveys() {
        List<SurveyEntity> allSurveys = surveyRepository.findAll();
        allSurveys.forEach(SurveyEntity::deleteEntity);
        surveyRepository.deleteAll();
    }
}
