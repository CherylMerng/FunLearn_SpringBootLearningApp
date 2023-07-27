package sg.nus.iss.facialrecognition.model;

import java.util.List;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Data
@Entity
@Table(name= "Videos")
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int videoId;

    @NotBlank(message = "The title cannot be blank")
    @Column(name= "title",columnDefinition = "nvarchar(150) not null")
    private String title;

    @NotBlank(message = "The description cannot be blank")
    @Column(name= "description",columnDefinition = "nvarchar(150) not null")
    private String description;

    @Column
    private String videoURL;
    @Column
    private String imageURL;
    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate postDate;

    @OneToMany(mappedBy = "video")
    private List<VideoWatched> watchBy;

    public Video(String title, String desc, String vidURL){
        this.title = title;
        this.description = desc;
        this.videoURL = vidURL;
        this.postDate = LocalDate.now();
    }

    public Video(String title, String desc, String vidURL, String imgURL, LocalDate date){
        this.title = title;
        this.description = desc;
        this.videoURL = vidURL;
        this.imageURL = imgURL;
        this.postDate = date;
    }

}
