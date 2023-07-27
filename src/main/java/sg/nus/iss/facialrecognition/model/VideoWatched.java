package sg.nus.iss.facialrecognition.model;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class VideoWatched {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int videoWatchedId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateWatched;

    @ManyToOne
    private Video video;

    @ManyToOne
    private User user;

}
