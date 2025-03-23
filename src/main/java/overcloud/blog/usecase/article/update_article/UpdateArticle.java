package overcloud.blog.usecase.article.update_article;

public interface UpdateArticle {
    Void updateArticle(String articleId, UpdateArticleRequest request);
}
