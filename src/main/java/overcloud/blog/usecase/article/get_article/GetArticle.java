package overcloud.blog.usecase.article.get_article;

public interface GetArticle {
    ArticleResponse getArticle(String articleId);
}
