package sg.nus.iss.facialrecognition.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import sg.nus.iss.facialrecognition.model.*;
import java.util.List;

@EnableJpaRepositories
@Repository
public interface SurveyQuestionScoreRepository extends JpaRepository<SurveyQuestionScore, Integer> {
    @Query ("SELECT s FROM SurveyQuestionScore s WHERE s.surveyScore.id = :id" )
    public List<SurveyQuestionScore> findSurveyQuestionScoreBySurveyScoreID(@Param("id")int id);
}
