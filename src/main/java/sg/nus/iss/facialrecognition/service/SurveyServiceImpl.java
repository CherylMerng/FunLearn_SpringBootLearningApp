package sg.nus.iss.facialrecognition.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import sg.nus.iss.facialrecognition.model.*;
import sg.nus.iss.facialrecognition.repository.*;

@Service
public class SurveyServiceImpl implements SurveyService {

    @Autowired
    private SurveyRepository surveyRepo;

    @Override
    public Survey saveSurvey(Survey survey){
        return surveyRepo.save(survey);
    }

    @Override
    public List<Survey> getAllSurveys(){
        return surveyRepo.findAll();
    }
    @Override
    public Survey getQOLSurvey(){
        return surveyRepo.findById(1).get();
    }

    @Override
    public Survey getPerceptionSurvey(){
        return surveyRepo.findById(2).get();
    }

    
}
