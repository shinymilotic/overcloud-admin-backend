package overcloud.blog.usecase.user.get_user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserResponse {
    private String id;
    private String username;
    private String email;
    private String bio;
    private String image;
}
