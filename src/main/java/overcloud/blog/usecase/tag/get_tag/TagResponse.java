package overcloud.blog.usecase.tag.get_tag;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TagResponse {
    private String id;
    private String tagName;
}
