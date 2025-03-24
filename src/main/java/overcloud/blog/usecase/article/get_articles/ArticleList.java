package overcloud.blog.usecase.article.get_articles;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class ArticleList {
    @JsonProperty("articles")
    private List<Article> articles;

    @JsonProperty("articlesCount")
    private int articlesCount;
}
