package overcloud.blog.usecase.user.create_user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import overcloud.blog.utils.validation.LetterAndNumber;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
    @NotBlank(message = "user.create.username.not-blank")
    @Size(min = 6, max = 32, message = "user.create.username.size")
    @LetterAndNumber(message = "user.create.username.letter-number")
    private String username;
    @NotBlank(message = "user.create.email.not-blank")
    @Email(message = "user.create.email.valid")
    private String email;
    @NotBlank(message = "user.create.password.not-blank")
    @Size(min = 8, max = 64, message = "user.create.password.size")
    private String password;
    private String bio;
    private String image;
    private boolean enabled;
}
