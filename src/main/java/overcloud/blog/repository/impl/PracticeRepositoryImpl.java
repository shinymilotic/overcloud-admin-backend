package overcloud.blog.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import overcloud.blog.entity.PracticeEntity;
import overcloud.blog.repository.PracticeRepository;
import overcloud.blog.repository.jparepository.JpaPracticeRepository;
import java.util.*;

@Repository
public class PracticeRepositoryImpl implements PracticeRepository {
    private final JpaPracticeRepository jpa;
    private final EntityManager entityManager;

    public PracticeRepositoryImpl(JpaPracticeRepository jpa, EntityManager entityManager) {
        this.jpa = jpa;
        this.entityManager = entityManager;
    }

    @Override
    public List<Tuple> findByTesterId(UUID testerId) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT p.practiceId, t.title, p.createdAt FROM PracticeEntity p ");
        query.append("INNER JOIN TestEntity t ON p.testId = t.testId ");
        query.append("WHERE p.testerId = :testerId ORDER BY p.createdAt DESC ");

        TypedQuery<Tuple> practiceQuery = entityManager
                .createQuery(query.toString(), Tuple.class)
                .setParameter("testerId", testerId);

        return practiceQuery.getResultList();
    }

    @Override
    public Optional<PracticeEntity> findById(UUID id) {
        return jpa.findById(id);
    }

    @Override
    public PracticeEntity save(PracticeEntity entity) {
        return jpa.save(entity);
    }

    @Override
    public void deleteByTestId(UUID testId) {
        jpa.deleteByTestId(testId);
    }

    @Override
    public List<UUID> findByTestId(UUID testId) {
        return jpa.findByTestId(testId);
    }
}
