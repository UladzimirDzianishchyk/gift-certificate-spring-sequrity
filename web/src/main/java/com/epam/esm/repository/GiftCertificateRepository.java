package com.epam.esm.repository;

import com.epam.esm.GiftCertificate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long> {

    Optional<GiftCertificate> findByName(String name);
    Page<GiftCertificate> findByNameContainingIgnoreCase(Pageable pageable, String name);
    Page<GiftCertificate> findByDescriptionContainingIgnoreCase(Pageable pageable, String description);
    Page<GiftCertificate> findGiftCertificateByTags_NameContainingIgnoreCase(String name, Pageable pageable);
}
