package sg.nus.iss.facialrecognition.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import sg.nus.iss.facialrecognition.model.*;
import sg.nus.iss.facialrecognition.repository.*;

@Service
public class SurveyQuestionScoreServiceImpl implements SurveyQuestionScoreService {

    @Autowired
    private SurveyQuestionScoreRepository surveyQuestionScoreRepo;

    @Override
    public SurveyQuestionScore saveSurveyQuestionScore(SurveyQuestionScore surveyQuestionScore){
        return surveyQuestionScoreRepo.save(surveyQuestionScore);
    }
    @Override
    public List<SurveyQuestionScore> findSurveyQuestionScoreBySurveyScoreID(int id){
        return surveyQuestionScoreRepo.findSurveyQuestionScoreBySurveyScoreID(id);
    }

 
        
    




    
}
