package overcloud.blog.usecase.tag.get_tags;

public interface GetTagList {
    TagListResponse getTagList(int pageNumber, int itemsPerPage);
}
