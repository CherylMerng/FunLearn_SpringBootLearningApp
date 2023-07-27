package sg.nus.iss.facialrecognition.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import sg.nus.iss.facialrecognition.model.*;
import sg.nus.iss.facialrecognition.repository.*;

@Service
public class SurveyScoreServiceImpl implements SurveyScoreService {

    @Autowired
    private SurveyScoreRepository surveyScoreRepo;

    @Override
    public SurveyScore saveSurveyScore(SurveyScore surveyScore){
        return surveyScoreRepo.save(surveyScore);
    }

    @Override
    public List<SurveyScore> findSurveyScoreByUsername(String username){
        return surveyScoreRepo.findSurveyScoreByUsername(username);
    }

    @Override
    public SurveyScore getSurveyScoreById(int id){
        return surveyScoreRepo.findById(id).get();
    }
    
}
