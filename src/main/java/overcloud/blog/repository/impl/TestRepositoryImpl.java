package overcloud.blog.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import overcloud.blog.entity.TestEntity;
import overcloud.blog.repository.TestRepository;
import overcloud.blog.repository.jparepository.JpaTestRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TestRepositoryImpl implements TestRepository {
    private final JpaTestRepository jpa;

    @PersistenceContext
    private final EntityManager entityManager;

    public TestRepositoryImpl(JpaTestRepository jpa, EntityManager entityManager) {
        this.jpa = jpa;
        this.entityManager = entityManager;
    }

    @Override
    public Optional<TestEntity> findById(UUID id) {
        return jpa.findById(id);
    }

    @Override
    public TestEntity save(TestEntity testEntity) {
        return jpa.save(testEntity);
    }

    @Override
    public void deleteById(UUID id) {
        jpa.deleteById(id);
    }

    @Override
    public void updateTest(UUID testId, String title, String description) {
        jpa.updateTest(testId, title, description);
    }

    public List<TestEntity> getProfileTest(UUID userId) {
        return this.jpa.findByUserId(userId);
    }

    @Override
    public void deleteByUserId(UUID userId) {
        jpa.deleteByUserId(userId);
    }
}
