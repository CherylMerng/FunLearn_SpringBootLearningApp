package sg.nus.iss.facialrecognition.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @NotBlank(message = "The Username cannot be blank")
    private String userName;
    @NotBlank(message = "The Password cannot be blank")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message = "The password must be valid with at least one uppercase English letter, one lowercase English letter, at least one digit, at least one special character and minimum eight in length.")
    private String currentPassword;
    @NotBlank(message = "The Password cannot be blank")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message = "The password must be valid with at least one uppercase English letter, one lowercase English letter, at least one digit, at least one special character and minimum eight in length.")
    private String newPassword;
    @NotBlank(message = "The Password cannot be blank")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message = "The password must be valid with at least one uppercase English letter, one lowercase English letter, at least one digit, at least one special character and minimum eight in length.")
    private String confirmPassword;
}
