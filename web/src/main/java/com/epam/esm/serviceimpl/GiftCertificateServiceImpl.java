package com.epam.esm.serviceimpl;

import com.epam.esm.DTO.GiftCertificateDtoWithTags;
import com.epam.esm.GiftCertificate;
import com.epam.esm.Tag;
import com.epam.esm.exception.EntityIsExistException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.utils.ObjectMapperUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateRepository certificateRepository;
    private final TagRepository tagRepository;

    public GiftCertificateServiceImpl(GiftCertificateRepository certificateRepository, TagRepository tagRepository) {
        this.certificateRepository = certificateRepository;
        this.tagRepository = tagRepository;
    }

    @Transactional
    @Override
    public GiftCertificateDtoWithTags newCertificate(GiftCertificate giftCertificate) {
        if (certificateRepository.findByName(giftCertificate.getName()).isPresent())
            throw new EntityIsExistException(giftCertificate.getName());
        GiftCertificate result = certificateRepository.save(loadTagsToSave(giftCertificate));
        return ObjectMapperUtils.map(result, GiftCertificateDtoWithTags.class);
    }

    @Transactional
    @Override
    public GiftCertificateDtoWithTags update(GiftCertificate giftCertificate) {
        GiftCertificate dbCert = certificateRepository.findById(giftCertificate.getId())
                .orElseThrow(() -> new EntityNotFoundException("Certificate " + giftCertificate.getName()));
        loadTagsToSave(giftCertificate);
        giftCertificate.getTags().addAll(dbCert.getTags());
        ObjectMapperUtils.mapObjects(giftCertificate, dbCert);
        return ObjectMapperUtils.map(certificateRepository.save(dbCert), GiftCertificateDtoWithTags.class);
    }

    @Override
    public GiftCertificateDtoWithTags updatePrice(Long id, Double price) {
        GiftCertificate cert = certificateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Certificate " + id));
        cert.setPrice(price);
        return ObjectMapperUtils.map(certificateRepository.save(cert), GiftCertificateDtoWithTags.class);
    }

    @Override
    public GiftCertificateDtoWithTags findById(Long id) {
        return ObjectMapperUtils.map(certificateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("" + id)), GiftCertificateDtoWithTags.class);
    }

    @Override
    public Page<GiftCertificateDtoWithTags> find(Pageable pageable) {
        return ObjectMapperUtils.mapEntityPageIntoDtoPage(certificateRepository.findAll(pageable), GiftCertificateDtoWithTags.class);
    }

    @Override
    public String deleteById(Long id) {
        if (!certificateRepository.existsById(id))
            throw new EntityNotFoundException("Certificate " + id);
        certificateRepository.deleteById(id);
        return "Certificate " + id + "deleted";
    }

    @Override
    public Page<GiftCertificateDtoWithTags> getCertByTag(String tagName, Pageable pageable) {
        return ObjectMapperUtils.mapEntityPageIntoDtoPage(certificateRepository
                .findGiftCertificateByTags_NameContainingIgnoreCase(tagName, pageable), GiftCertificateDtoWithTags.class);
    }

    @Override
    public Page<GiftCertificateDtoWithTags> getCertByName(Pageable pageable, String name) {
        return ObjectMapperUtils.mapEntityPageIntoDtoPage(certificateRepository
                .findByNameContainingIgnoreCase(pageable, name), GiftCertificateDtoWithTags.class);
    }

    @Override
    public Page<GiftCertificateDtoWithTags> getCertByDescription(Pageable pageable, String description) {
        return ObjectMapperUtils.mapEntityPageIntoDtoPage(certificateRepository
                .findByDescriptionContainingIgnoreCase(pageable, description), GiftCertificateDtoWithTags.class);
    }

    GiftCertificate loadTagsToSave(GiftCertificate giftCertificate) {
        Set<Tag> loadTags = new HashSet<>();

        for (Tag t : giftCertificate.getTags()) {
            Optional<Tag> tag = tagRepository.findByNameIgnoreCase(t.getName());
            if (tag.isPresent())
                loadTags.add(tag.get());
            else
                loadTags.add(t);
        }
        giftCertificate.setTags(loadTags);
        return giftCertificate;
    }
}
