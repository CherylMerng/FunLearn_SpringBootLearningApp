package sg.nus.iss.facialrecognition.service;

import sg.nus.iss.facialrecognition.model.*;

import java.util.*;

public interface SurveyQuestionScoreService {
    public SurveyQuestionScore saveSurveyQuestionScore(SurveyQuestionScore surveyQuestionScore);
    public List<SurveyQuestionScore> findSurveyQuestionScoreBySurveyScoreID(int id);

}
