package overcloud.blog.usecase.user.get_users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserListResponse {
    private List<UserResponse> users;
    private Integer userCount;
}
