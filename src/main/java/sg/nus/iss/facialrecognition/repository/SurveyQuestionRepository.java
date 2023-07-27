package sg.nus.iss.facialrecognition.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import sg.nus.iss.facialrecognition.model.*;
import java.util.List;

@EnableJpaRepositories
@Repository
public interface SurveyQuestionRepository extends JpaRepository<SurveyQuestion, Integer> {

}
