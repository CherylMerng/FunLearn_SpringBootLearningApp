package sg.nus.iss.facialrecognition.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.nus.iss.facialrecognition.model.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import sg.nus.iss.facialrecognition.repository.QuizRepository;

@Service
public class QuizServiceImpl implements QuizService {
    @Autowired
    private QuizRepository quizRepo;
    @Autowired
    private UserService userService;

    @Override
    public Quiz saveQuiz(Quiz quiz){
        return quizRepo.save(quiz);
    }

    @Override
    public List<Quiz> getAllQuiz(){
        return quizRepo.findAll();
    }

    @Override
    public List<Quiz> getQuizAtLeastOneMonthAgo(String username){
        LocalDate todayDate = LocalDate.now();
        LocalDate dateOneMonthAgo = todayDate.minusMonths(1);
        List<Quiz> quizAttemptAtLeastOneMonthAgo = quizRepo.findQuizAtLeastOneMonthAgo(dateOneMonthAgo, username);
        return quizAttemptAtLeastOneMonthAgo;
    }

    @Override
    public List<Quiz> getQuizThisMonth(String username){
        LocalDate todayDate = LocalDate.now();
        LocalDate dateOneMonthAgo = todayDate.minusMonths(1);
        return quizRepo.findQuizThisMonth(dateOneMonthAgo, username);
    }
    @Override
    public Map<LocalDate, Integer> getDateToQuizMapThisMonth(String username){
        List<Quiz> q = getQuizThisMonth(username);
        Map<LocalDate, Integer> map = new HashMap<LocalDate, Integer>();
        Map<LocalDate, List<Quiz>> dateToQuiz = q.stream().collect(Collectors.groupingBy(Quiz:: getAttemptDate));
        dateToQuiz.forEach((Date, quiz)-> {
            map.put(Date, quiz.size());
        });
        return map;

    }

    @Override
    public Map<User, List<Quiz>> getUserToQuiz(List<User> users){
        Map<User, List<Quiz>> userToQuiz = new HashMap<User,List<Quiz>>();
        for (User u: users){
            List<Quiz> q = quizRepo.findByUser(u);
            Collections.sort(q,new Comparator<Quiz>(){
                @Override
                public int compare (Quiz q1, Quiz q2){
                    return q2.getAttemptDate().compareTo(q1.getAttemptDate());
                }
            });
            userToQuiz.put(u,q);
        }
        return userToQuiz;
    }

    @Override
    public Quiz getQuizById(int id){
        return quizRepo.findById(id).get();
    }


    
}
