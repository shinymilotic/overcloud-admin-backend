package overcloud.blog.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import overcloud.blog.usecase.tag.create_tag.CreateTagRequest;
import overcloud.blog.usecase.tag.create_tag.CreateTag;
import overcloud.blog.usecase.tag.delete_tag.DeleteTag;
import overcloud.blog.usecase.tag.get_tag.GetTag;
import overcloud.blog.usecase.tag.get_tag.TagResponse;
import overcloud.blog.usecase.tag.get_tags.GetTagList;
import overcloud.blog.usecase.tag.get_tags.TagListResponse;
import overcloud.blog.usecase.tag.update_tag.UpdateTag;
import overcloud.blog.usecase.tag.update_tag.UpdateTagRequest;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController("tags")
public class TagController {
    private final CreateTag createTagService;
    private final GetTagList getTagsAdmin;
    private final DeleteTag deleteTag;
    private final UpdateTag updateTag;
    private final GetTag getTag;

    public TagController(CreateTag createTagService,
                         GetTagList getTagsAdmin,
                         DeleteTag deleteTag,
                         UpdateTag updateTag,
                         GetTag getTag) {
        this.createTagService = createTagService;
        this.getTagsAdmin = getTagsAdmin;
        this.deleteTag = deleteTag;
        this.updateTag = updateTag;
        this.getTag = getTag;
    }

    @PostMapping("")
    public List<String> createTags(@RequestBody CreateTagRequest createTagRequest) {
        return createTagService.createTags(createTagRequest);
    }

    @GetMapping("")
    public TagListResponse getTagList(@RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                                      @RequestParam(value = "itemsPerPage", defaultValue = "10") int itemsPerPage) {
        return getTagsAdmin.getTagList(pageNumber, itemsPerPage);
    }

    @DeleteMapping("")
    public Void deleteTag(@RequestParam(value = "tagId") String tagId) {
        return deleteTag.deleteTag(tagId);
    }

    @GetMapping("/{tagId}")
    public TagResponse getTag(@PathVariable("tagId") String tagId) {
        return getTag.getTag(tagId);
    }

    @PutMapping("/{tagId}")
    public Void updateTag(@PathVariable("tagId") String tagId,
                          @RequestBody UpdateTagRequest request) {
        return updateTag.updateTag(tagId, request);
    }
}
