package overcloud.blog.usecase.user.get_users;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import overcloud.blog.entity.UserEntity;
import overcloud.blog.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetUserListServiceImpl implements GetUserListService {
    private final UserRepository userRepository;

    public GetUserListServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public UserListResponse getUsers(int pageNumber, int itemsPerPage) {
        List<UserEntity> users = userRepository.findAll(pageNumber, itemsPerPage);
        UserListResponse response = new UserListResponse();

        List<UserResponse> userList = users.stream()
            .map(this::toUserResponse)
            .collect(Collectors.toList());
        response.setUsers(userList);
        response.setUserCount(userList.size());

        return response;
    }

    private UserResponse toUserResponse(UserEntity userEntity) {
        UserResponse response = new UserResponse();
        response.setId(userEntity.getUserId().toString());
        response.setUsername(userEntity.getUsername());
        response.setEmail(userEntity.getEmail());
        response.setBio(userEntity.getBio());
        response.setImage(userEntity.getImage());
        return response;
    }
}
