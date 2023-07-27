package sg.nus.iss.facialrecognition.service;

import sg.nus.iss.facialrecognition.model.*;

import java.util.*;

public interface SurveyQuestionService {
    public SurveyQuestion saveSurveyQuestion(SurveyQuestion surveyQuestion);
    public Map<Survey, List<SurveyQuestion>> getSurveyToQuestions();
    public List<SurveyQuestion> getAllSurveyQuestions();
    public SurveyQuestion getSurveyQuestionById(int id);

}
