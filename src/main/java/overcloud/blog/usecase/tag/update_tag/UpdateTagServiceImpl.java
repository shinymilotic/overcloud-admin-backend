package overcloud.blog.usecase.tag.update_tag;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import overcloud.blog.entity.TagEntity;
import overcloud.blog.repository.TagRepository;
import overcloud.blog.utils.validation.ObjectsValidator;
import java.util.Optional;
import java.util.UUID;

@Service
public class UpdateTagServiceImpl implements UpdateTag {
    private final TagRepository tagRepository;
    private final ObjectsValidator validator;
    public static final String TAG_NO_EXIST = "admin.update-tag.tag-no-exists";

    public UpdateTagServiceImpl(TagRepository tagRepository,
                                ObjectsValidator validator) {
        this.tagRepository = tagRepository;
        this.validator = validator;
    }

    @Override
    @Transactional
    public Void updateTag(String tagId, UpdateTagRequest tag) {
        UUID uuidTagId = UUID.fromString(tagId) ;
        Optional<TagEntity> tagEntityOptional = tagRepository.findByTagId(uuidTagId);

        if (tagEntityOptional.isEmpty()) {
            throw validator.fail(TAG_NO_EXIST);
        }

        tagRepository.save(tagEntityOptional.get());
        return null;
    }
}
