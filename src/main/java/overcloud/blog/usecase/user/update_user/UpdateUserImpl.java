package overcloud.blog.usecase.user.update_user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import overcloud.blog.repository.UserRepository;

@Service
public class UpdateUserImpl implements UpdateUser {
    private final UserRepository userRepository;

    public UpdateUserImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Void updateUser(String userId, UpdateUserRequest request) {
        return null;
    }
}
