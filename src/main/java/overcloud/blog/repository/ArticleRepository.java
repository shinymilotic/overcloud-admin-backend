package overcloud.blog.repository;
import overcloud.blog.entity.ArticleEntity;
import java.util.Optional;
import java.util.UUID;

public interface ArticleRepository {
    Optional<ArticleEntity> findById(UUID id);
    void deleteById(UUID id);
    Optional<Boolean> isTitleExist(String title);
    void save(ArticleEntity articleEntity);
    void deleteByUserId(UUID userId);
    void updateSearchVector();
}
