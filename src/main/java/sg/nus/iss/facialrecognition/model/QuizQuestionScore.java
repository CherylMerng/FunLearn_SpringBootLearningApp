package sg.nus.iss.facialrecognition.model;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table
public class QuizQuestionScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    private boolean isCorrect;
    @Column
    private String emotionGuessed;
    @ManyToOne
    private QuizQuestion question;
    @ManyToOne
    private Quiz quiz;

}
