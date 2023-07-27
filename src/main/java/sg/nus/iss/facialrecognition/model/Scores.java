package sg.nus.iss.facialrecognition.model;

import lombok.NoArgsConstructor;
import lombok.Data;
import java.util.*;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class Scores {
    private int qolScore;
    private int perceptionScore;
    private List<SurveyQuestionScore> questionScores;
}
