package overcloud.blog.usecase.user.get_user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import overcloud.blog.entity.UserEntity;
import overcloud.blog.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class GetUserImpl implements GetUser {
    private final UserRepository userRepository;

    public GetUserImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserResponse getUser(String userId) {
        UUID uuidUserId = UUID.fromString(userId);
        Optional<UserEntity> userEntityOptional = userRepository.findById(uuidUserId);

        return userEntityOptional.map(this::toUserResponse).orElse(null);

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
