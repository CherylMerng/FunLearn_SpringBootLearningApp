package sg.nus.iss.facialrecognition.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import sg.nus.iss.facialrecognition.model.QuizQuestion;

@EnableJpaRepositories
@Repository
public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Integer> {

}
