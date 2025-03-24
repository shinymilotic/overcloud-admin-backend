package overcloud.blog.usecase.article.create_article;

import com.github.f4b6a3.uuid.UuidCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import overcloud.blog.auth.service.SpringAuthenticationService;
import overcloud.blog.entity.*;
import overcloud.blog.exception.InvalidDataException;
import overcloud.blog.repository.ArticleRepository;
import overcloud.blog.repository.ArticleTagRepository;
import overcloud.blog.repository.TagRepository;
import overcloud.blog.response.ApiError;
import overcloud.blog.usecase.user.common.UserResMsg;
import overcloud.blog.utils.validation.ObjectsValidator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CreateArticleImpl implements CreateArticle {
    private final SpringAuthenticationService authenticationService;
    private final TagRepository tagRepository;
    private final ArticleRepository articleRepository;
    private final ObjectsValidator<CreateArticleRequest> validator;
    private final ArticleTagRepository articleTagRepository;

    public CreateArticleImpl(SpringAuthenticationService authenticationService,
                                TagRepository tagRepository,
                                ArticleRepository articleRepository,
                                ObjectsValidator<CreateArticleRequest> validator,
                                ArticleTagRepository articleTagRepository) {
        this.authenticationService = authenticationService;
        this.tagRepository = tagRepository;
        this.articleRepository = articleRepository;
        this.validator = validator;
        this.articleTagRepository = articleTagRepository;
    }

    @Override
    @Transactional
    public UUID createArticle(CreateArticleRequest articleRequest) {
        String title = articleRequest.getTitle();
        List<String> distinctTags = filterDistinctTags(articleRequest.getTagList());
        articleRequest.setTagList(distinctTags);
        Optional<ApiError> apiError = validator.validate(articleRequest);

        if (apiError.isPresent()) {
            throw new InvalidDataException(apiError.get());
        }

        Optional<Boolean> isExist = articleRepository.isTitleExist(title);

        if (isExist.isPresent()) {
            throw validator.fail("article.create.title.exists");
        }

        List<TagEntity> tagEntities = tagRepository.findByTagIds(articleRequest.getTagList());
        if (distinctTags.size() > tagEntities.size()) {
            throw validator.fail("article.create.tagList.no-exists");
        }

        UserEntity currentUser = authenticationService.getCurrentUser()
                .orElseThrow(() -> validator.fail(UserResMsg.USER_NOT_FOUND))
                .getUser();
        ArticleEntity articleEntity = toArticleEntity(articleRequest, currentUser);
        List<ArticleTag> articleTags = toArticleTagEntity(tagEntities, articleEntity);
        articleRepository.save(articleEntity);
        articleTagRepository.saveAll(articleTags);
        articleRepository.updateSearchVector();

        return articleEntity.getArticleId();
    }

    private List<String> filterDistinctTags(List<String> tags) {
        if (tags != null) {
            tags = tags.stream().distinct().toList();
        } else {
            tags = new ArrayList<>();
        }

        return tags;
    }

    public ArticleEntity toArticleEntity(CreateArticleRequest articleRequest, UserEntity author) {
        String title = articleRequest.getTitle();
        String body = articleRequest.getBody();
        String description = articleRequest.getDescription();
        LocalDateTime now = LocalDateTime.now();

        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setArticleId(UuidCreator.getTimeOrderedEpoch());
        articleEntity.setAuthorId(author.getUserId());
        articleEntity.setBody(body);
        articleEntity.setDescription(description);
        articleEntity.setTitle(title);
        articleEntity.setCreatedAt(now);
        articleEntity.setUpdatedAt(now);

        return articleEntity;
    }

    public List<ArticleTag> toArticleTagEntity(List<TagEntity> tagEntities, ArticleEntity articleEntity) {
        return tagEntities.stream()
                .map(tagEntity -> {
                    ArticleTagId articleTagId = new ArticleTagId();
                    articleTagId.setArticleId(articleEntity.getArticleId());
                    articleTagId.setTagId(tagEntity.getTagId());
                    return new ArticleTag(articleTagId);
                })
                .collect(Collectors.toList());
    }
}
