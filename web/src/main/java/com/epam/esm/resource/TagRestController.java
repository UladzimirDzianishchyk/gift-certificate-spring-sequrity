package com.epam.esm.resource;

import com.epam.esm.DTO.TagDto;
import com.epam.esm.Tag;
import com.epam.esm.service.TagService;
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

/**
 *Tag rest controller
 *   api/v1//tags
 */

@RestController
@RequestMapping(path = "/tags")
public class TagRestController {

    private final TagService tagService;

    public TagRestController(TagService tagService) {
        this.tagService = tagService;
    }


    /**
     * Get tag by id
     *  /{id}
     * @param id int
     * @return tagDto
     */
    @GetMapping(path = "{id}")
    public ResponseEntity<TagDto> findById(@PathVariable long id) {
        TagDto result = tagService.findById(id);
        result.add(WebMvcLinkBuilder.linkTo(methodOn(TagRestController.class)
                        .deleteById(id))
                .withRel("Delete tag " + result.getName()));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    /**
     *Get all tags by page and sort
     * @param size int
     * @param page int
     * @param sort String
     * @return Page<TagDto>
     */
    @GetMapping
    public ResponseEntity<?> showAll(@RequestParam int size, int page, String sort,PagedResourcesAssembler<TagDto> assembler) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<TagDto> result = tagService.find(pageable);

        for (TagDto t : result.getContent()) {
            Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(TagRestController.class)
                            .findById(t.getId()))
                    .withRel("Tag " + t.getName());
            t.add(selfLink);
        }

        return new ResponseEntity<>(assembler.toModel(result), HttpStatus.OK);
    }

    @GetMapping(path = "find-by-name")
    public ResponseEntity<?> findByName(@RequestParam int size, int page, String name, String sort,PagedResourcesAssembler<TagDto> assembler){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<TagDto> result = tagService.findByName(pageable,name);
        return new ResponseEntity<>(assembler.toModel(result), HttpStatus.OK);
    }

    /**
     * Delete tag by id
     *  /{id}
     * @param id int
     * @return String
     */
    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        return new ResponseEntity<>(tagService.deleteById(id), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<TagDto> save(@RequestBody Tag tag) {
        TagDto result = tagService.newTag(tag);
        result.add(WebMvcLinkBuilder.linkTo(methodOn(TagRestController.class)
                        .deleteById(result.getId()))
                .withRel("Delete tag " + result.getId()));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping(path = "find-most-popular-end-expensive-tag-by-user-id")
    public TagDto findMostPopularEndExpensiveTagByUserId(@RequestParam Long userId){
        return tagService.findMostPopularEndExpensiveTagByUserId(userId);
    }
}
