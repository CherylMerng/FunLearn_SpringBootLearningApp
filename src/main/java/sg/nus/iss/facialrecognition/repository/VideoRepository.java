package sg.nus.iss.facialrecognition.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import sg.nus.iss.facialrecognition.model.Quiz;
import sg.nus.iss.facialrecognition.model.QuizQuestion;
import sg.nus.iss.facialrecognition.model.Video;
import java.util.*;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

@EnableJpaRepositories
@Repository
public interface VideoRepository extends JpaRepository<Video, Integer> {

    @Query("SELECT v FROM Video v WHERE v.title LIKE %:input% AND v.description LIKE %:input% ORDER BY v.title")
    List<Video> findVideosBySearchString(@Param ("input")String input); 
}
