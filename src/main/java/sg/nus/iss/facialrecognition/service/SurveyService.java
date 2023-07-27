package sg.nus.iss.facialrecognition.service;

import sg.nus.iss.facialrecognition.model.*;

import java.util.*;

public interface SurveyService {
    public Survey saveSurvey(Survey survey);
    public List<Survey> getAllSurveys();
    public Survey getQOLSurvey();
    public Survey getPerceptionSurvey();

}
