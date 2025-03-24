package overcloud.blog.usecase.article.update_article;

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
    public Void updateArticle(String articleId, UpdateArticleRequest updateArticleRequest) {
        Optional<ApiError> apiError = validator.validate(updateArticleRequest);
        if (apiError.isPresent()) {
            throw new InvalidDataException(apiError.get());
        }
        List<String> tags = updateArticleRequest.getTagList();
        Optional<ArticleEntity> articleEntities = articleRepository.findById(UUID.fromString(articleId));
        List<TagEntity> tagEntities = tagRepository.findByTagIds(updateArticleRequest.getTagList());

        if (articleEntities.isEmpty()) {
            throw validator.fail("article.update.no-exists");
        }
        ArticleEntity articleEntity = articleEntities.get();

        List<ArticleTag> articleTags = new ArrayList<>();
        for (String tag : tags) {
            UUID tagId = UUID.fromString(tag);
            Optional<TagEntity> tagEntity = isTagExist(tagId, tagEntities);

            if (tagEntity.isEmpty()) {
                throw validator.fail("article.update.tagList.no-exists");
            } else {
                ArticleTagId articleTagId = new ArticleTagId(articleEntity.getArticleId(), tagEntity.get().getTagId());
                articleTags.add(new ArticleTag(articleTagId));
            }
        }

        UserEntity currentUser = authenticationService.getCurrentUser()
                .orElseThrow(() -> validator.fail(UserResMsg.USER_NOT_FOUND))
                .getUser();

        LocalDateTime now = LocalDateTime.now();
        articleEntity.setTitle(updateArticleRequest.getTitle());
        articleEntity.setDescription(updateArticleRequest.getDescription());
        articleEntity.setBody(updateArticleRequest.getBody());
        articleEntity.setUpdatedAt(now);

        articleRepository.save(articleEntity);
        articleTagRepository.deleteByArticleId(articleEntity.getArticleId());
        articleTagRepository.saveAll(articleTags);
        articleRepository.updateSearchVector();

        return null;
    }

    private Optional<TagEntity> isTagExist(UUID tagId, List<TagEntity> tagEntities) {
        for (TagEntity tagEntity : tagEntities) {
            if (tagEntity.getTagId().equals(tagId)) {
                return Optional.of(tagEntity);
            }
        }
        return Optional.empty();
    }
}
