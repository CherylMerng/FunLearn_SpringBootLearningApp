package sg.nus.iss.facialrecognition.model;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table
public class FamilyMember {
    @Id
    @Column
    private String userName;

    @ManyToOne
    private User user;


}
