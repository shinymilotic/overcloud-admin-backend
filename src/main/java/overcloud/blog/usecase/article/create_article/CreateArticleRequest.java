package overcloud.blog.usecase.article.create_article;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Builder
@Getter
@Setter
public class CreateArticleRequest {
    @JsonProperty("title")
    @Size(min = 1, max = 60, message = "article.create.title.size")
    private String title;

    @JsonProperty("description")
    @NotBlank(message = "article.create.description.not-blank")
    @Size(min = 1, max = 100, message = "article.create.description.size")
    private String description;

    @JsonProperty("body")
    @NotBlank(message = "article.create.body.not-blank")
    private String body;

    @JsonProperty("tagList")
    @NotEmpty(message = "article.create.tagList.not-empty")
    private List<String> tagList;
}

