package overcloud.blog.repository;
import overcloud.blog.entity.ArticleEntity;
import overcloud.blog.usecase.article.get_articles.Article;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ArticleRepository {
    Optional<ArticleEntity> findById(UUID id);
    void deleteById(UUID id);
    Optional<Boolean> isTitleExist(String title);
    void save(ArticleEntity articleEntity);
    void deleteByUserId(UUID userId);
    void updateSearchVector();
    List<ArticleEntity> findArticles(int pageNumber, int itemsPerPage);
}
