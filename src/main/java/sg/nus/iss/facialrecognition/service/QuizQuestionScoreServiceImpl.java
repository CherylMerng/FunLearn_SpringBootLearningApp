package sg.nus.iss.facialrecognition.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.nus.iss.facialrecognition.model.QuizQuestionScore;
import sg.nus.iss.facialrecognition.repository.QuizQuestionScoreRepository;

@Service
public class QuizQuestionScoreServiceImpl implements QuizQuestionScoreService {

    @Autowired
    private QuizQuestionScoreRepository quizQuestionScoreRepo;
    @Override
    public QuizQuestionScore saveQuizQuestionScore(QuizQuestionScore quizQuestionScore){
        return quizQuestionScoreRepo.save(quizQuestionScore);
    }
}


    
