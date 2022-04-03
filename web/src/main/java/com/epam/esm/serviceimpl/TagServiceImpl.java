package com.epam.esm.serviceimpl;

import com.epam.esm.DTO.TagDto;
import com.epam.esm.Tag;
import com.epam.esm.exception.EntityIsExistException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.utils.ObjectMapperUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public TagDto newTag(Tag tag) {
        if (tagRepository.findByNameIgnoreCase(tag.getName()).isPresent())
            throw new EntityIsExistException(tag.getName());
        return ObjectMapperUtils.map(tagRepository.save(tag), TagDto.class);
    }

    @Override
    public TagDto findById(Long id) {
        return ObjectMapperUtils.map(tagRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tag " + id)),TagDto.class);
    }

    @Override
    public Page<TagDto> findByName(Pageable pageable,String name) {
        Page<Tag> page = tagRepository
                .findByNameContainingIgnoreCase(name, pageable);
        if (page.getContent().isEmpty()) throw new EntityNotFoundException("Tag " + name);
        return ObjectMapperUtils.mapEntityPageIntoDtoPage(page,TagDto.class);
    }

    @Override
    public Page<TagDto> find(Pageable pageable) {
        return ObjectMapperUtils.mapEntityPageIntoDtoPage(
                tagRepository.findAll(pageable),TagDto.class);
    }

    @Override
    public String deleteById(Long id) {
        if (!tagRepository.existsById(id)) throw new EntityNotFoundException("Tag " + id);
        tagRepository.deleteById(id);
        return "Tag " + id + "deleted";
    }

    @Override
    public TagDto findMostPopularEndExpensiveTagByUserId(Long userId) {
        return ObjectMapperUtils.map(tagRepository.findMostPopularEndExpensiveTagByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("User " + userId)), TagDto.class);
    }
}
