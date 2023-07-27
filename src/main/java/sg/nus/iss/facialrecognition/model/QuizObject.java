package sg.nus.iss.facialrecognition.model;

import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
public class QuizObject {
    private int id;
    private int question1;
    private int question2;
    private int question3;
    private int question4;
    private int question5;
    private int question6;
    private int question7;
    private int question8;
    private int question9;
    private int question10;
    private String question1Guess;
    private String question2Guess;
    private String question3Guess;
    private String question4Guess;
    private String question5Guess;
    private String question6Guess;
    private String question7Guess;
    private String question8Guess;
    private String question9Guess;
    private String question10Guess;
}
