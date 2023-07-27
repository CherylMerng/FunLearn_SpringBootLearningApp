package sg.nus.iss.facialrecognition.service;

import sg.nus.iss.facialrecognition.model.*;

import java.util.*;

public interface SurveyScoreService {
    public SurveyScore saveSurveyScore(SurveyScore surveyScore);
    public List<SurveyScore> findSurveyScoreByUsername(String username);
    public SurveyScore getSurveyScoreById(int id);


}
