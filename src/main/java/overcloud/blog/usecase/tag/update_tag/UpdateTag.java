package overcloud.blog.usecase.tag.update_tag;

public interface UpdateTag {
    Void updateTag(String tagId, UpdateTagRequest tag);
}
