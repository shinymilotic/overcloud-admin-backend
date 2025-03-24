package overcloud.blog.usecase.article.create_article;

import java.util.UUID;

public interface CreateArticle {
    UUID createArticle(CreateArticleRequest request);
}
