package overcloud.blog.usecase.article.update_article;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateArticleRequest {
    @JsonProperty("title")
    @Size(min = 1, max = 60, message = "article.update.title.size")
    private String title;

    @JsonProperty("description")
    @NotBlank(message = "article.update.description.not-blank")
    @Size(min = 1, max = 100, message = "article.update.description.size")
    private String description;

    @JsonProperty("body")
    @NotBlank(message = "article.update.body.not-blank")
    private String body;

    @JsonProperty("tagList")
    @NotEmpty(message = "article.update.tagList.not-empty")
    private List<String> tagList;
}
