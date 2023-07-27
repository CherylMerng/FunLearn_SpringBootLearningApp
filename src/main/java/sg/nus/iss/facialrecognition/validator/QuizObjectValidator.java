package sg.nus.iss.facialrecognition.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import sg.nus.iss.facialrecognition.model.QuizObject;



@Component
public class QuizObjectValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz){
        return QuizObject.class.isAssignableFrom(clazz);
    }
    @Override
    public void validate(Object obj, Errors errors){
        QuizObject quiz = (QuizObject) obj;
        if (quiz.getQuestion1Guess().equals("None")){
            errors.rejectValue("question1Guess", "error.question1Guess", "Please select an emotion");
        }
        if (quiz.getQuestion2Guess().equals("None")){
            errors.rejectValue("question2Guess", "error.question2Guess", "Please select an emotion");
        }
        if (quiz.getQuestion3Guess().equals("None")){
            errors.rejectValue("question3Guess", "error.question3Guess", "Please select an emotion");
        }
        if (quiz.getQuestion4Guess().equals("None")){
            errors.rejectValue("question4Guess", "error.question4Guess", "Please select an emotion");
        }
        if (quiz.getQuestion5Guess().equals("None")){
            errors.rejectValue("question5Guess", "error.question5Guess", "Please select an emotion");
        }
        if (quiz.getQuestion6Guess().equals("None")){
            errors.rejectValue("question6Guess", "error.question6Guess", "Please select an emotion");
        }
        if (quiz.getQuestion7Guess().equals("None")){
            errors.rejectValue("question7Guess", "error.question7Guess", "Please select an emotion");
        }
        if (quiz.getQuestion8Guess().equals("None")){
            errors.rejectValue("question8Guess", "error.question8Guess", "Please select an emotion");
        }
        if (quiz.getQuestion9Guess().equals("None")){
            errors.rejectValue("question9Guess", "error.question9Guess", "Please select an emotion");
        }
        if (quiz.getQuestion10Guess().equals("None")){
            errors.rejectValue("question10Guess", "error.question10Guess", "Please select an emotion");
        }
        
    }
    
}
