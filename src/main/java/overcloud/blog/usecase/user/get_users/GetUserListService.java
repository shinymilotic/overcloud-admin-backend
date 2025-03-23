package overcloud.blog.usecase.user.get_users;

public interface GetUserListService {
    UserListResponse getUsers(int pageNumber, int itemsPerPage);
}
