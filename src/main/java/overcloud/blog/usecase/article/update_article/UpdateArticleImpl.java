package overcloud.blog.usecase.article.update_article;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import overcloud.blog.auth.service.SpringAuthenticationService;
import overcloud.blog.repository.ArticleRepository;
import overcloud.blog.repository.ArticleTagRepository;
import overcloud.blog.repository.TagRepository;
import overcloud.blog.utils.validation.ObjectsValidator;

@Service
public class UpdateArticleImpl implements UpdateArticle {
    private final SpringAuthenticationService authenticationService;
    private final ArticleRepository articleRepository;
    private final TagRepository tagRepository;
    private final ArticleTagRepository articleTagRepository;
    private final ObjectsValidator<UpdateArticleRequest> validator;

    public UpdateArticleImpl(SpringAuthenticationService authenticationService,
                             ArticleRepository articleRepository,
                             TagRepository tagRepository,
                             ArticleTagRepository articleTagRepository,
                             ObjectsValidator<UpdateArticleRequest> validator) {
        this.authenticationService = authenticationService;
        this.articleRepository = articleRepository;
        this.tagRepository = tagRepository;
        this.articleTagRepository = articleTagRepository;
        this.validator = validator;
    }

    @Override
    @Transactional
    public Void updateArticle(String articleId, UpdateArticleRequest request) {
        return null;
    }
}
