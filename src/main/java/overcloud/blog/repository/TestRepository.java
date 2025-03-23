package overcloud.blog.repository;

import overcloud.blog.entity.TestEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TestRepository {
    Optional<TestEntity> findById(UUID id);
    TestEntity save(TestEntity testEntity);
    void deleteById(UUID id);
    void updateTest(UUID testId, String title, String description);
    List<TestEntity> getProfileTest(UUID userId);
    void deleteByUserId(UUID userId);
}
