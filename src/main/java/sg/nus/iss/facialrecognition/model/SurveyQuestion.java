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
public class SurveyQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    private String description;
    public SurveyQuestion(String description){
        this.description = description;
    }
    @ManyToOne
    private Survey survey;
    @OneToMany(mappedBy = "surveyQuestion")
    private List<SurveyQuestionScore> surveyQuestionScores;

}
