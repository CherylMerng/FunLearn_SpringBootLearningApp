package sg.nus.iss.facialrecognition.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
@Table
public class User implements Comparable<User> {
    @Id
    @Column
    private String userName;

    @NotBlank(message = "The Full Name cannot be blank")
    @Column(columnDefinition = "nvarchar(150) not null")
    private String fullName;

    @NotBlank(message = "The Password cannot be blank")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message = "The password must be valid with at least one uppercase English letter, one lowercase English letter, at least one digit, at least one special character and minimum eight in length.")
    private String password;

    @NotBlank(message = "The Email cannot be blank")
    @Email(message = "Email should be valid")
    @Size(max = 200)
    private String email;
    private String parentName;
    public User(String userName, String fullName, String password, String email, String parentName){
        this.userName = userName;
        this.fullName = fullName;
        this.password = password;
        this.email = email;
        this.parentName=parentName;
    }

    @ManyToOne
    private Role role;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate lastAccountAccess;

    @Column
    private boolean isActive;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<FamilyMember> members;



    @OneToMany (mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Quiz> quizzes;

    @OneToMany (mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<VideoWatched> videoWatched;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<SurveyScore> surveyScores;

    private String token;

    @Override
    public int compareTo(User other){
        return userName.compareTo(other.userName);
    }
}
