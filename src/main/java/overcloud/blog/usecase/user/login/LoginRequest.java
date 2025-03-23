package overcloud.blog.usecase.user.login;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import static overcloud.blog.usecase.user.login.LoginResMsg.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class LoginRequest {
    @JsonProperty("email")
    @Size(min = 3, max = 256, message = EMAIL_SIZE)
    @Email(message = EMAIL_VALID)
    private String email;

    @JsonProperty("password")
    @NotNull(message = PASSWORD_NOTNULL)
    private String password;
}
