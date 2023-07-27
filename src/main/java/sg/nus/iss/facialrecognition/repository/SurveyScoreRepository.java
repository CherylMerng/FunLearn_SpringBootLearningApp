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
public interface SurveyScoreRepository extends JpaRepository<SurveyScore, Integer> {
    @Query ("SELECT s FROM SurveyScore s WHERE s.user.userName = :username" )
    public List<SurveyScore> findSurveyScoreByUsername(@Param("username")String username);

}
