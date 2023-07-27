package sg.nus.iss.facialrecognition.service;
import java.time.LocalDate;
import java.util.*;
import sg.nus.iss.facialrecognition.model.*;


public interface QuizService {
    public Quiz saveQuiz(Quiz quiz);

    public Quiz getQuizById(int id);

    public List<Quiz> getAllQuiz();

    public List<Quiz> getQuizAtLeastOneMonthAgo(String username);

    public List<Quiz> getQuizThisMonth(String username);

    public Map<LocalDate, Integer> getDateToQuizMapThisMonth(String username);

    public Map<User, List<Quiz>> getUserToQuiz(List<User> user);

}
