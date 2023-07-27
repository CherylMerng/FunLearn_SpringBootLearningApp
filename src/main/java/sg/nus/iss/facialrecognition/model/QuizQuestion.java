package sg.nus.iss.facialrecognition.model;

import java.util.List;
import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table
public class QuizQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Lob
    @Column(columnDefinition="BLOB")
    //save image as Blob (large binary object for images)
    private byte[] data;
    private String actualEmotion;
    @OneToMany(mappedBy ="question")
    private List<QuizQuestionScore> scores;

}
