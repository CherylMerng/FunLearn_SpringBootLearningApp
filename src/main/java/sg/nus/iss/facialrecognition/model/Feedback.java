package sg.nus.iss.facialrecognition.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackId;

    @NotBlank(message = "The subject cannot be blank")
    private String subject;

    @NotBlank(message = "The feedback cannot be blank.")
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate feedbackDate;

    @Column(name = "has_read")
    private boolean read;

    private String reply;

    @ManyToOne
    private User user;

    public Feedback(String subject, String desc) {
        this.subject = subject;
        this.description = desc;
        this.feedbackDate = LocalDate.now();
        this.read = false;
    }
}
