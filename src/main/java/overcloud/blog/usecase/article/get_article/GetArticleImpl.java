package overcloud.blog.usecase.article.get_article;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import overcloud.blog.entity.ArticleEntity;
import overcloud.blog.repository.ArticleRepository;
import overcloud.blog.utils.validation.ObjectsValidator;

import java.util.Optional;
import java.util.UUID;

@Service
public class GetArticleImpl implements GetArticle {
    private final ArticleRepository articleRepository;
    private final ObjectsValidator validator;

    public GetArticleImpl(ArticleRepository articleRepository,
                          ObjectsValidator validator) {
        this.articleRepository = articleRepository;
        this.validator = validator;
    }

    @Override
    @Transactional
    public ArticleResponse getArticle(String articleId) {
        Optional<ArticleEntity> articleEntityOptional = articleRepository.findById(UUID.fromString(articleId));

        return articleEntityOptional.map(this::toArticleResponse).orElse(null);
    }

    private ArticleResponse toArticleResponse(ArticleEntity articleEntity) {
        ArticleResponse response = new ArticleResponse();
        response.setId(articleEntity.getArticleId().toString());
        response.setTitle(articleEntity.getTitle());
        response.setDescription(articleEntity.getDescription());
        response.setBody(articleEntity.getBody());
        response.setCreatedAt(articleEntity.getCreatedAt().toString());
        return response;
    }
}
