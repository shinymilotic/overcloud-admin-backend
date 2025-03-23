package overcloud.blog.usecase.tag.get_tag;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import overcloud.blog.entity.TagEntity;
import overcloud.blog.repository.TagRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class GetTagImpl implements GetTag {
    private final TagRepository tagRepository;

    public GetTagImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    @Transactional
    public TagResponse getTag(String tagId) {
        UUID uuidTagId = UUID.fromString(tagId);

        Optional<TagEntity> tagEntityOptional = tagRepository.findByTagId(uuidTagId);

        if (tagEntityOptional.isPresent()) {
            TagEntity tagEntity = tagEntityOptional.get();
            TagResponse response = new TagResponse();
            response.setId(tagEntity.getTagId().toString());
            response.setTagName(tagEntity.getName());
            return response;
        }

        return null;
    }
}
