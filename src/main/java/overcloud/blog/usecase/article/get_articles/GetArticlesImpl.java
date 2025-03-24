package overcloud.blog.usecase.article.get_articles;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import overcloud.blog.auth.bean.SecurityUser;
import overcloud.blog.auth.service.SpringAuthenticationService;
import overcloud.blog.entity.ArticleEntity;
import overcloud.blog.entity.UserEntity;
import overcloud.blog.repository.ArticleRepository;
import overcloud.blog.utils.validation.ObjectsValidator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GetArticlesImpl implements GetArticles {
    private final ArticleRepository articleRepository;
    private final SpringAuthenticationService authenticationService;
    private final ObjectsValidator validator;

    public GetArticlesImpl(ArticleRepository articleRepository,
                           SpringAuthenticationService authenticationService,
                           ObjectsValidator validator) {
        this.articleRepository = articleRepository;
        this.authenticationService = authenticationService;
        this.validator = validator;
    }

    @Override
    @Transactional
    public ArticleList getArticles(int pageNumber, int itemsPerPage) {
        Optional<SecurityUser> currentSecurityUser = authenticationService.getCurrentUser();
        ArticleList response = new ArticleList();
        UserEntity currentUser = null;
        UUID currentUserId = null;
        if (currentSecurityUser.isPresent()) {
            currentUser = currentSecurityUser.get().getUser();
            currentUserId = currentUser.getUserId();
        }
        List<ArticleEntity> articleEntities = articleRepository.findArticles(pageNumber, itemsPerPage);
        List<Article> articles = articleEntities.stream().map(this::toArticle).toList();

        response.setArticlesCount(articles.size());
        response.setArticles(articles);

        return response;
    }

    private Article toArticle(ArticleEntity entity) {
        Article article = new Article();
        article.setId(entity.getArticleId().toString());
        article.setTitle(entity.getTitle());
        article.setDescription(entity.getDescription());
        article.setCreatedAt(entity.getCreatedAt().toString());

        return article;
    }

}
