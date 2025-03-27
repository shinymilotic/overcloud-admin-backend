package overcloud.blog.repository;

import overcloud.blog.entity.UserEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    UserEntity findByUsername(String username);
    UserEntity findByEmail(String email);
    UserEntity save(UserEntity entity);
    List<UserEntity> findAll(int page, int size);
    UserEntity findRolesByUsernname(String username);
    void deleteUser(UUID userId);
    Optional<UserEntity> findById(UUID userId);
}
