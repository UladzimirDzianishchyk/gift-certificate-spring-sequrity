package com.epam.esm.service;

import com.epam.esm.DTO.TagDto;
import com.epam.esm.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TagService {

    TagDto newTag(Tag tag);

    TagDto findById(Long id);

    Page<TagDto> findByName(Pageable pageable, String name);

    Page<TagDto> find(Pageable pageable);

    String deleteById(Long id);

    TagDto findMostPopularEndExpensiveTagByUserId(Long userId);
}
