package sg.nus.iss.facialrecognition.service;


import sg.nus.iss.facialrecognition.model.QuizQuestion;

public interface QuizQuestionService {
    public QuizQuestion saveQuizQuestion(QuizQuestion question);
    public QuizQuestion findQuizQuestion(int id);
}
