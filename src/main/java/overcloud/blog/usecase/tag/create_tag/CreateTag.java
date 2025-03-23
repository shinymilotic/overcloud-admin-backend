package overcloud.blog.usecase.tag.create_tag;

import java.util.List;

public interface CreateTag {
    List<String> createTags(CreateTagRequest createTagRequest);
}
