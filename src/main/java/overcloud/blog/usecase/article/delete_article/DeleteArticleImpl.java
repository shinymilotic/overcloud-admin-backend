package overcloud.blog.usecase.article.delete_article;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import overcloud.blog.repository.ArticleRepository;
import overcloud.blog.repository.ArticleTagRepository;
import overcloud.blog.repository.CommentRepository;
import overcloud.blog.repository.FavoriteRepository;
import java.util.UUID;

@Service
public class DeleteArticleImpl implements DeleteArticle {
    private final ArticleRepository articleRepository;
    private final ArticleTagRepository articleTagRepository;
    private final CommentRepository commentRepository;
    private final FavoriteRepository favoriteRepository;

    public DeleteArticleImpl(ArticleRepository articleRepository,
                             ArticleTagRepository articleTagRepository,
                             CommentRepository commentRepository,
                             FavoriteRepository favoriteRepository) {
        this.articleRepository = articleRepository;
        this.articleTagRepository = articleTagRepository;
        this.commentRepository = commentRepository;
        this.favoriteRepository = favoriteRepository;
    }

    @Override
    @Transactional
    public Void deleteArticle(String articleId) {
        UUID uuidArticleId = UUID.fromString(articleId);
        commentRepository.deleteByArticleId(uuidArticleId);
        favoriteRepository.deleteByArticleId(uuidArticleId);
        articleTagRepository.deleteByArticleId(uuidArticleId);
        articleRepository.deleteById(uuidArticleId);
        articleRepository.updateSearchVector();

        return null;    }
}
