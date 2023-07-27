package sg.nus.iss.facialrecognition.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table
public class SurveyQuestionScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    private int questionScore;
    @ManyToOne
    private SurveyScore surveyScore;
    @ManyToOne
    private SurveyQuestion surveyQuestion;

}
