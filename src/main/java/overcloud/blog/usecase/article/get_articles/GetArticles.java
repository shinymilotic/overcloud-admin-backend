package overcloud.blog.usecase.article.get_articles;

public interface GetArticles {
    ArticleList getArticles(int pageNumber, int itemsPerPage);
}
