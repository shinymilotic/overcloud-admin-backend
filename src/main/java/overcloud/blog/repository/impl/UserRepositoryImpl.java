package overcloud.blog.repository.impl;

import jakarta.persistence.*;
import org.springframework.stereotype.Repository;
import overcloud.blog.entity.UserEntity;
import overcloud.blog.repository.UserRepository;
import overcloud.blog.repository.jparepository.JpaUserRepository;
import java.util.List;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final JpaUserRepository jpa;
    private final EntityManager entityManager;

    public UserRepositoryImpl(JpaUserRepository jpa, EntityManager entityManager) {
        this.jpa = jpa;
        this.entityManager = entityManager;
    }

    @Override
    public UserEntity findByUsername(String username) {
        return jpa.findByUsername(username);
    }

    @Override
    public UserEntity findByEmail(String email) {
        return jpa.findByEmail(email);
    }

    @Override
    public UserEntity save(UserEntity entity) {
        return jpa.save(entity);
    }

    @Override
    public List<UserEntity> findAll(int pageNumber, int itemsPerPage) {
        return entityManager
                .createQuery("SELECT users FROM UserEntity users", UserEntity.class)
                .setFirstResult((itemsPerPage * pageNumber) - itemsPerPage)
                .setMaxResults(itemsPerPage)
                .getResultList();
    }

    @Override
    public UserEntity findRolesByUsernname(String username) {
        return jpa.findRolesByUsernname(username);
    }

    @Override
    public void deleteUser(UUID userId) {
        jpa.deleteById(userId);
    }
}
