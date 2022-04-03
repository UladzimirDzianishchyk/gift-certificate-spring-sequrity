package com.epam.esm.service;

import com.epam.esm.DTO.TagDto;
import com.epam.esm.Tag;
import com.epam.esm.exception.EntityIsExistException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.serviceimpl.TagServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TagServiceTest {

    TagRepository tagRepositoryMoc = Mockito.mock(TagRepository.class);

    Tag tag = new Tag("test");
    TagDto tagDto = new TagDto();
    List<Tag> tags = Arrays.asList(tag);
    List<TagDto> dtos = new ArrayList<>();

    Pageable pageable = PageRequest.of(1, 2);
    Page<Tag> page = new PageImpl<>(tags, pageable, 2);

    TagService tagService = new TagServiceImpl(tagRepositoryMoc);

    @Test
    void newTag() {
        tag.setId(1L);
        tagDto.setId(1L);
        Mockito.when(tagRepositoryMoc.save(tag)).thenReturn(tag);
        assertEquals(tagService.newTag(tag).getId(), tagDto.getId());
        Mockito.when(tagRepositoryMoc.findByNameIgnoreCase("test")).thenReturn(Optional.of(tag));
        assertThrows(EntityIsExistException.class, () -> tagService.newTag(tag));
    }

    @Test
    void findById() {
        tag.setId(1L);
        Mockito.when(tagRepositoryMoc.findById(1L)).thenReturn(Optional.of(tag));
        assertEquals(tagService.findById(1L).getId(), 1L);
        assertThrows(EntityNotFoundException.class, () -> tagService.findById(5L));
    }

    @Test
    void findByName() {
        Mockito.when(tagRepositoryMoc.findByNameContainingIgnoreCase(tag.getName(),pageable)).thenReturn(page);
        assertEquals(tagService.findByName(pageable,tag.getName()).getContent().size(),1);
        Mockito.when(tagRepositoryMoc.findByNameContainingIgnoreCase(tag.getName(),pageable)).thenReturn(Page.empty());
        assertThrows(EntityNotFoundException.class, () -> tagService.findByName(pageable,tag.getName()));
    }

    @Test
    void find() {
        tagDto.setName("test");
        dtos.add(tagDto);
        Mockito.when(tagRepositoryMoc.findAll(pageable)).thenReturn(page);
        assertEquals(tagService.find(pageable).getContent(),dtos);
    }

    @Test
    void deleteById() {
        Mockito.when(tagRepositoryMoc.existsById(5L)).thenReturn(true);
        assertEquals(String.class, tagService.deleteById(5L).getClass());
        Mockito.when(tagRepositoryMoc.existsById(1L)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> tagService.deleteById(1L));
    }

    @Test
    void findMostPopularEndExpensiveTagByUserId() {
        tag.setId(1L);
        tagDto.setId(1L);
        Mockito.when(tagRepositoryMoc.findMostPopularEndExpensiveTagByUserId(1L)).thenReturn(Optional.of(tag));
        Mockito.when(tagRepositoryMoc.findMostPopularEndExpensiveTagByUserId(5L)).thenReturn(Optional.empty());
        assertEquals(tagService.findMostPopularEndExpensiveTagByUserId(1L).getId(),tagDto.getId());
        assertThrows(EntityNotFoundException.class, () -> tagService.findMostPopularEndExpensiveTagByUserId(5L));

    }
}