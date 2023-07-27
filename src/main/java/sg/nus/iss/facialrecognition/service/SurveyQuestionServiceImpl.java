package sg.nus.iss.facialrecognition.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import sg.nus.iss.facialrecognition.model.*;
import sg.nus.iss.facialrecognition.repository.*;
import java.util.stream.Collectors;

@Service
public class SurveyQuestionServiceImpl implements SurveyQuestionService {

    @Autowired
    private SurveyQuestionRepository surveyQuestionRepo;

    @Override
    public SurveyQuestion saveSurveyQuestion(SurveyQuestion surveyQuestion){
        return surveyQuestionRepo.save(surveyQuestion);
    }
    @Override
    public Map<Survey, List<SurveyQuestion>> getSurveyToQuestions(){
        List<SurveyQuestion> allSurveyQuestions = surveyQuestionRepo.findAll();
        Map<Survey, List<SurveyQuestion>> surveyToQuestions = allSurveyQuestions.stream().collect(Collectors.groupingBy(SurveyQuestion :: getSurvey));
        return surveyToQuestions;
    }

    @Override
    public List<SurveyQuestion> getAllSurveyQuestions(){
        return surveyQuestionRepo.findAll();
    }

    @Override
    public SurveyQuestion getSurveyQuestionById(int id){
        return surveyQuestionRepo.findById(id).get();
    }
        
    




    
}
