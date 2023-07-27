package sg.nus.iss.facialrecognition.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.nus.iss.facialrecognition.model.QuizQuestion;
import sg.nus.iss.facialrecognition.repository.QuizQuestionRepository;

@Service
public class QuizQuestionServiceImpl implements QuizQuestionService {

    @Autowired
    private QuizQuestionRepository quizQuestionRepo;

    @Override
    public QuizQuestion saveQuizQuestion(QuizQuestion question) {
        return quizQuestionRepo.save(question);
      }
    @Override
    public QuizQuestion findQuizQuestion(int id){
        return quizQuestionRepo.findById(id).get();
    }

    
}
