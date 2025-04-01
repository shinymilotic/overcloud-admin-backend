package overcloud.blog.usecase.user.get_current_user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import overcloud.blog.auth.service.SpringAuthenticationService;
import overcloud.blog.entity.UserEntity;
import overcloud.blog.repository.UserRepository;
import overcloud.blog.usecase.user.common.UserResMsg;
import overcloud.blog.utils.validation.ObjectsValidator;

@Service
public class GetCurrentUserImpl implements GetCurrentUser {
    private final UserRepository userRepository;
    private final SpringAuthenticationService authenticationService;
    private final ObjectsValidator validator;

    public GetCurrentUserImpl(UserRepository userRepository,
                              SpringAuthenticationService authenticationService,
                              ObjectsValidator validator) {
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
        this.validator = validator;
    }

    @Override
    @Transactional
    public CurrentUser getCurrentUser() {
        UserEntity currentUser = authenticationService.getCurrentUser()
                .orElseThrow(() -> validator.fail(UserResMsg.USER_NOT_FOUND))
                .getUser();

        return toCurrentUser(currentUser);
    }

    private CurrentUser toCurrentUser(UserEntity currentUser) {
        CurrentUser response = new CurrentUser();
        response.setId(currentUser.getUserId().toString());
        response.setUsername(currentUser.getUsername());
        response.setEmail(currentUser.getEmail());
        response.setImage(currentUser.getImage());
        response.setBio(currentUser.getBio());
        return response;
    }
}
