package overcloud.blog.controller;

import jakarta.servlet.http.HttpServletResponse;
import overcloud.blog.usecase.user.create_user.CreateUserRequest;
import overcloud.blog.usecase.user.create_user.CreateUser;
import overcloud.blog.usecase.user.get_user.GetUser;
import overcloud.blog.usecase.user.get_user.UserResponse;
import overcloud.blog.usecase.user.get_users.GetUserListService;
import overcloud.blog.usecase.user.get_users.UserListResponse;
import overcloud.blog.usecase.user.delete_user.DeleteUser;
import org.springframework.web.bind.annotation.*;
import overcloud.blog.usecase.user.login.Login;
import overcloud.blog.usecase.user.login.LoginRequest;
import overcloud.blog.usecase.user.login.LoginResponse;
import overcloud.blog.usecase.user.update_user.UpdateUser;
import overcloud.blog.usecase.user.update_user.UpdateUserRequest;
import java.io.IOException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserController {
    private final GetUserListService getUserListService;
    private final CreateUser createUser;
    private final DeleteUser deleteUser;
    private final UpdateUser updateUser;
    private final Login login;
    private final GetUser getUser;

    public UserController(GetUserListService getUserListService,
                          CreateUser createUser,
                          DeleteUser deleteUser,
                          UpdateUser updateUser,
                          Login login,
                          GetUser getUser) {
        this.getUserListService = getUserListService;
        this.createUser = createUser;
        this.deleteUser = deleteUser;
        this.updateUser = updateUser;
        this.login = login;
        this.getUser = getUser;
    }

    @GetMapping("/users")
    public UserListResponse getUsers(@RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber, @RequestParam(value = "itemsPerPage", defaultValue = "10") int itemsPerPage) {
        return getUserListService.getUsers(pageNumber, itemsPerPage);
    }

    @PostMapping("/users")
    public Void createUser(@RequestBody CreateUserRequest request) throws IOException {
        return createUser.createUser(request);
    }

    @DeleteMapping("/users/{userId}")
    public Void deleteUser(@PathVariable("userId") String userId) {
        return deleteUser.deleteUser(userId);
    }

    @PutMapping("/users/{userId}")
    public Void updateUser(@PathVariable("userId") String userId,
                           @RequestBody UpdateUserRequest request) {
        return updateUser.updateUser(userId, request);
    }

    @GetMapping("/users/{userId}")
    public UserResponse getUser(@PathVariable("userId") String userId) {
        return getUser.getUser(userId);
    }

    @PostMapping("/users/login")
    public LoginResponse login(@RequestBody LoginRequest loginDto, HttpServletResponse response) {
        return login.login(loginDto, response);
    }
}
