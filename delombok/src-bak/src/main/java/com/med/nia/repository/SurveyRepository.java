package com.med.nia.repository;

import com.med.nia.model.SurveyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyRepository extends JpaRepository<SurveyEntity, Integer> {

    /**
     * Delete all survey with ids specified in {@code ids} parameter
     *
     * @param ids List of user ids
     */
    @Modifying
    @Query(value = "delete from Surveys s where s.id in ?1", nativeQuery = true)
    void deleteSurveysWithIds(List<Integer> ids);

}
