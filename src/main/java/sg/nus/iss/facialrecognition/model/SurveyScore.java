package sg.nus.iss.facialrecognition.model;
import java.time.LocalDate;
import java.util.*;
import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@NoArgsConstructor
@Data
@Entity
@Table
public class SurveyScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate attemptDate;
    @Column
    private int totalScore;
    @ManyToOne
    private User user;
    @ManyToOne
    private Survey survey;
    @OneToMany(mappedBy ="surveyScore")
    private List<SurveyQuestionScore> surveyQuestionScores;

}
