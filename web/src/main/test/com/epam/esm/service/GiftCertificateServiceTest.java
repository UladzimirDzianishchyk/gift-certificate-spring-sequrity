package com.epam.esm.service;

import com.epam.esm.DTO.GiftCertificateDtoWithTags;
import com.epam.esm.GiftCertificate;
import com.epam.esm.Tag;
import com.epam.esm.exception.EntityIsExistException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.serviceimpl.GiftCertificateServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GiftCertificateServiceTest {

    GiftCertificateRepository giftCertificateRepository = Mockito.mock(GiftCertificateRepository.class);
    TagRepository tagRepository = Mockito.mock(TagRepository.class);

    Tag tag = new Tag("test");
    Set<Tag> tags = new HashSet<>();
    GiftCertificate gc = new GiftCertificate();
    List<GiftCertificate> giftCertificates = Arrays.asList(gc);
    Pageable pageable = PageRequest.of(1, 2);
    Page<GiftCertificate> page = new PageImpl<>(giftCertificates, pageable, 2);

    GiftCertificateService giftCertificateService = new GiftCertificateServiceImpl(giftCertificateRepository, tagRepository);

    @Test
    void newCertificate() {
        gc.setName("test");
        gc.setTags(tags);
        Mockito.when(giftCertificateRepository.findByName("test")).thenReturn(Optional.of(gc));
        assertThrows(EntityIsExistException.class,() -> giftCertificateService.newCertificate(gc));
        Mockito.when(giftCertificateRepository.findByName("test")).thenReturn(Optional.empty());
        Mockito.when(giftCertificateRepository.save(gc)).thenReturn(gc);
        assertEquals(giftCertificateService.newCertificate(gc).getName(),"test");

    }

    @Test
    void update() {
        tags.add(tag);
        gc.setId(1L);
        gc.setTags(tags);
        Mockito.when(giftCertificateRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> giftCertificateService.update(gc));
        Mockito.when(giftCertificateRepository.findById(1L)).thenReturn(Optional.of(gc));
        Mockito.when(giftCertificateRepository.save(gc)).thenReturn(gc);
        assertEquals(giftCertificateService.update(gc).getId(), 1L);

    }

    @Test
    void updatePrice() {
        gc.setId(1L);
        gc.setPrice(2D);
        Mockito.when(giftCertificateRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> giftCertificateService.update(gc));
        Mockito.when(giftCertificateRepository.findById(1L)).thenReturn(Optional.of(gc));
        Mockito.when(giftCertificateRepository.save(gc)).thenReturn(gc);
        assertEquals(giftCertificateService.updatePrice(1L, 5D).getPrice(), 5D);
    }

    @Test
    void findById() {
        gc.setId(1L);
        Mockito.when(giftCertificateRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> giftCertificateService.findById(1L));
        Mockito.when(giftCertificateRepository.findById(1L)).thenReturn(Optional.of(gc));
        assertEquals(giftCertificateService.findById(1L).getId(), 1L);
    }

    @Test
    void find() {
        Mockito.when(giftCertificateRepository.findAll(pageable)).thenReturn(page);
        assertEquals(giftCertificateService.find(pageable).getContent().size(), 1);
    }

    @Test
    void deleteById() {
        Mockito.when(giftCertificateRepository.existsById(1L)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> giftCertificateService.deleteById(1L));
        Mockito.when(giftCertificateRepository.existsById(1L)).thenReturn(true);
        assertEquals(String.class, giftCertificateService.deleteById(1L).getClass());
    }

    @Test
    void getCertByTag() {
        Mockito.when(giftCertificateRepository.findGiftCertificateByTags_NameContainingIgnoreCase("test", pageable))
                .thenReturn(page);
        assertEquals(giftCertificateService.getCertByTag("test", pageable).getContent().size(), 1);
    }

    @Test
    void getCertByName() {
        Mockito.when(giftCertificateRepository.findByNameContainingIgnoreCase(pageable, "test"))
                .thenReturn(page);
        assertEquals(giftCertificateService.getCertByName(pageable, "test").getContent()
                .size(), 1);
    }

    @Test
    void getCertByDescription() {
        Mockito.when(giftCertificateRepository.findByDescriptionContainingIgnoreCase(pageable, "test"))
                .thenReturn(page);
        assertEquals(giftCertificateService.getCertByDescription(pageable, "test")
                .getContent().size(), 1);

    }
}