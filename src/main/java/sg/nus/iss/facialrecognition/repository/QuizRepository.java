package sg.nus.iss.facialrecognition.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sg.nus.iss.facialrecognition.model.*;

@EnableJpaRepositories
@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {

    @Query ("SELECT q FROM Quiz q WHERE q.attemptDate <= :dateOneMonthAgo AND q.user.userName = :username" )
	public List<Quiz> findQuizAtLeastOneMonthAgo(@Param("dateOneMonthAgo") LocalDate date, @Param("username")String username);

    @Query ("SELECT q FROM Quiz q WHERE q.attemptDate >= :dateOneMonthAgo AND q.user.userName = :username" )
	public List<Quiz> findQuizThisMonth(@Param("dateOneMonthAgo") LocalDate date, @Param("username")String username);

    public List<Quiz> findByUser(User user);


}
