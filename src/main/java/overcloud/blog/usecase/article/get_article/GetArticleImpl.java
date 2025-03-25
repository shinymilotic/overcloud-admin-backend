package overcloud.blog.usecase.article.get_article;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import overcloud.blog.entity.ArticleEntity;
import overcloud.blog.repository.ArticleRepository;
import java.util.Optional;
import java.util.UUID;

@Service
public class GetArticleImpl implements GetArticle {
    private final ArticleRepository articleRepository;

    public GetArticleImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    @Transactional
    public ArticleResponse getArticle(String articleId) {
        Optional<ArticleEntity> articleEntityOptional = articleRepository.findById(UUID.fromString(articleId));

        if (articleEntityOptional.isEmpty()) {

        }


        return toArticleResponse(articleEntityOptional.get());
    }

    private ArticleResponse toArticleResponse(ArticleEntity articleEntity) {
        
    }
}
