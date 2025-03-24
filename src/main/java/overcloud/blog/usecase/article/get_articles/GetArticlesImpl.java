package overcloud.blog.usecase.article.get_articles;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import overcloud.blog.auth.bean.SecurityUser;
import overcloud.blog.auth.service.SpringAuthenticationService;
import overcloud.blog.entity.ArticleEntity;
import overcloud.blog.entity.UserEntity;
import overcloud.blog.repository.ArticleRepository;
import overcloud.blog.utils.validation.ObjectsValidator;

import java.util.ArrayList;
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
        response.setArticlesCount(articleEntities.size());
        articleEntities.stream().

        return getArticlesResponse;
    }

    private GetArticlesSingleResponse toGetArticlesSingleResponse(ArticleSummary article) {
        return GetArticlesSingleResponse.builder()
                .id(article.getId().toString())
                .title(article.getTitle())
                .body(article.getBody())
                .description(article.getDescription())
                .createdAt(article.getCreatedAt().toLocalDateTime())
                .build();
    }
}
