package overcloud.blog.controller;

import org.springframework.web.bind.annotation.*;
import overcloud.blog.usecase.article.create_article.CreateArticle;
import overcloud.blog.usecase.article.create_article.CreateArticleRequest;
import overcloud.blog.usecase.article.delete_article.DeleteArticle;
import overcloud.blog.usecase.article.get_article.ArticleResponse;
import overcloud.blog.usecase.article.get_article.GetArticle;
import overcloud.blog.usecase.article.get_articles.ArticleList;
import overcloud.blog.usecase.article.get_articles.GetArticles;
import overcloud.blog.usecase.article.update_article.UpdateArticle;
import overcloud.blog.usecase.article.update_article.UpdateArticleRequest;

import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController("articles")
public class ArticleController {
    private final CreateArticle createArticle;
    private final UpdateArticle updateArticle;
    private final DeleteArticle deleteArticle;
    private final GetArticles getArticles;
    private final GetArticle getArticle;

    public ArticleController(CreateArticle createArticle,
                             UpdateArticle updateArticle,
                             DeleteArticle deleteArticle,
                             GetArticles getArticles,
                             GetArticle getArticle) {
        this.createArticle = createArticle;
        this.updateArticle = updateArticle;
        this.deleteArticle = deleteArticle;
        this.getArticles = getArticles;
        this.getArticle = getArticle;
    }

    @PostMapping("")
    public UUID createArticle(CreateArticleRequest request) {
        return createArticle.createArticle(request);
    }

    @PutMapping("/{articleId}")
    public Void updateArticle(@PathVariable("articleId") String articleId,
                              @RequestBody UpdateArticleRequest request) {
        return updateArticle.updateArticle(articleId, request);
    }

    @DeleteMapping("/{articleId}")
    public Void deleteArticle(@PathVariable("articleId") String articleId) {
        return deleteArticle.deleteArticle(articleId);
    }

    @GetMapping("/{articleId}")
    public ArticleResponse getArticle(@PathVariable("articleId") String articleId) {
        return getArticle.getArticle(articleId);
    }

    @GetMapping("")
    public ArticleList getArticles(@RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                                   @RequestParam(value = "itemsPerPage", defaultValue = "10") int itemsPerPage) {
        return getArticles.getArticles(pageNumber, itemsPerPage);
    }
}
