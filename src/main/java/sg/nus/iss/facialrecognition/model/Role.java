package sg.nus.iss.facialrecognition.model;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int roleID;

    @Column
    private String roleName;

    @OneToMany(mappedBy ="role")
    private List<User> users;

}
