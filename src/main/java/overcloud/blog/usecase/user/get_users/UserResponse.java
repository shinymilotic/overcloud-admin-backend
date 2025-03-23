package overcloud.blog.usecase.user.get_users;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private String id;
    private String username;
    private String email;
    private String bio;
    private String image;
}
