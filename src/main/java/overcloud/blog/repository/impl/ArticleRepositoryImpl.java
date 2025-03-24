package overcloud.blog.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import org.springframework.stereotype.Repository;
import overcloud.blog.entity.ArticleEntity;
import overcloud.blog.repository.ArticleRepository;
import overcloud.blog.repository.jparepository.JpaArticleRepository;
import java.sql.Timestamp;
import java.util.*;

@Repository
public class ArticleRepositoryImpl implements ArticleRepository {
    private final JpaArticleRepository jpa;
    private final EntityManager entityManager;

    public ArticleRepositoryImpl(JpaArticleRepository jpa, EntityManager entityManager) {
        this.jpa = jpa;
        this.entityManager = entityManager;
    }

    @Override
    public Optional<ArticleEntity> findById(UUID id) {
        return this.jpa.findById(id);
    }

    @Override
    public void deleteById(UUID id) {
        this.jpa.deleteById(id);
    }

    @Override
    public Optional<Boolean> isTitleExist(String title) {
        return this.jpa.isTitleExist(title);
    }


    @Override
    public void save(ArticleEntity articleEntity) {
        this.jpa.save(articleEntity);
    }

    @Override
    public void deleteByUserId(UUID userId) {
        jpa.deleteByUserId(userId);
    }

    @Override
    public void updateSearchVector() {
        this.jpa.updateSearchVector();
    }
}
