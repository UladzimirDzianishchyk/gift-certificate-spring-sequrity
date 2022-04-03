package com.epam.esm.resource;

import com.epam.esm.DTO.GiftCertificateDtoWithTags;
import com.epam.esm.DTO.TagDto;
import com.epam.esm.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/gift-certificates")
public class GiftCertificateRestController {


    private final GiftCertificateService giftCertificateService;

    public GiftCertificateRestController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }


    @GetMapping(path = "{id}")
    public ResponseEntity<GiftCertificateDtoWithTags> findById(@PathVariable long id) {
        GiftCertificateDtoWithTags result = giftCertificateService.findById(id);
        result.add(WebMvcLinkBuilder.linkTo(methodOn(GiftCertificateRestController.class)
                        .deleteById(id))
                .withRel("Delete certificate " + result.getName()));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<?> showAll(@RequestParam int size, int page, String sort, PagedResourcesAssembler<GiftCertificateDtoWithTags> assembler) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<GiftCertificateDtoWithTags> result = giftCertificateService.find(pageable);
        addLinksFromResource(result);
        return new ResponseEntity<>(assembler.toModel(result), HttpStatus.OK);
    }

    @GetMapping(path = "find-by-name")
    public ResponseEntity<?> getCertByName(@RequestParam int size, int page, String name, String sort, PagedResourcesAssembler<GiftCertificateDtoWithTags> assembler) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<GiftCertificateDtoWithTags> result = giftCertificateService.getCertByName(pageable, name);
        addLinksFromResource(result);
        return new ResponseEntity<>(assembler.toModel(result), HttpStatus.OK);
    }


    @GetMapping(path = "find-by-description")
    public ResponseEntity<?> getCertByDescription(@RequestParam int size, int page, String description, String sort, PagedResourcesAssembler<GiftCertificateDtoWithTags> assembler) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<GiftCertificateDtoWithTags> result = giftCertificateService.getCertByDescription(pageable, description);
        addLinksFromResource(result);
        return new ResponseEntity<>(assembler.toModel(result), HttpStatus.OK);
    }


    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> deleteById(@PathVariable long id) {
        return new ResponseEntity<>(giftCertificateService.deleteById(id), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<GiftCertificateDtoWithTags> save(@RequestBody GiftCertificate giftCertificate) {
        return new ResponseEntity<>(giftCertificateService.newCertificate(giftCertificate), HttpStatus.OK);
    }


    @PatchMapping
    public ResponseEntity<GiftCertificateDtoWithTags> update(@RequestBody GiftCertificate giftCertificate) {
        return new ResponseEntity<>(giftCertificateService.update(giftCertificate), HttpStatus.OK);
    }

    @PatchMapping(path = "update-price")
    public ResponseEntity<GiftCertificateDtoWithTags> updatePrice(@RequestParam Long id, Double price){
        return new ResponseEntity<>(giftCertificateService.updatePrice(id, price),HttpStatus.OK);
    }


    @GetMapping(path = "get-cert-by-tags")
    public ResponseEntity<?> getCertByTags(@RequestParam int size, int page, String sort, PagedResourcesAssembler<GiftCertificateDtoWithTags> assembler, String name) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<GiftCertificateDtoWithTags> result = giftCertificateService.getCertByTag(name, pageable);
        addLinksFromResource(result);
        return new ResponseEntity<>(assembler.toModel(result), HttpStatus.OK);
    }

    private void addLinksFromResource(Page<GiftCertificateDtoWithTags> result) {
        for (GiftCertificateDtoWithTags cert : result.getContent()) {
            for (TagDto t : cert.getTags()) {
                Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(TagRestController.class)
                                .findById(t.getId()))
                        .withRel("Tag " + t.getName());
                t.add(selfLink);
            }
            Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(GiftCertificateRestController.class)
                            .findById(cert.getId()))
                    .withRel("Certificate " + cert.getName());
            cert.add(selfLink);
        }
    }

}
