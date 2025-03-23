package overcloud.blog.usecase.user.update_user;

public interface UpdateUser {
    Void updateUser(String userId, UpdateUserRequest request);
}
