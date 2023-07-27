package sg.nus.iss.facialrecognition.model;

import java.util.List;


import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private int usualAverageScore;
    public Survey (String name, String description, int usualAverageScore){
        this.name = name;
        this.description = description;
        this.usualAverageScore = usualAverageScore;
    }
    @OneToMany(mappedBy= "survey")
    private List<SurveyQuestion> surveyQuestions;
    @OneToMany(mappedBy = "survey")
    private List<SurveyScore> surveyScores;

}
