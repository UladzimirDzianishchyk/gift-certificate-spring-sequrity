package com.epam.esm.service;

import com.epam.esm.DTO.GiftCertificateDtoWithTags;
import com.epam.esm.GiftCertificate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GiftCertificateService {

    GiftCertificateDtoWithTags newCertificate(GiftCertificate giftCertificate);

    GiftCertificateDtoWithTags update(GiftCertificate giftCertificate);

    GiftCertificateDtoWithTags updatePrice(Long id, Double Price);

    GiftCertificateDtoWithTags findById(Long id);

    Page<GiftCertificateDtoWithTags> find(Pageable pageable);

    String deleteById(Long id);

    Page<GiftCertificateDtoWithTags> getCertByTag(String tagName, Pageable pageable);

    Page<GiftCertificateDtoWithTags> getCertByName(Pageable pageable, String name);

    Page<GiftCertificateDtoWithTags> getCertByDescription(Pageable pageable, String description);

}
