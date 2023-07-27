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
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate attemptDate;
    @Column
    private int score;
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<QuizQuestionScore> questions;
    @ManyToOne
    private User user;

}
