package com.epam.esm.resource;


import com.epam.esm.DTO.UserDto;
import com.epam.esm.service.UserService;
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
 * User rest controller
 * api/v1//users
 */
@RestController
@RequestMapping(path = "/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Get user by id
     * /{id}
     *
     * @param id int
     * @return userDto
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable long id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    /**
     * Get all users by page and sort
     *
     * @param size int
     * @param page      int
     * @param sort      String
     * @return PaginationAndSort<userDto>
     */
    @GetMapping
    public ResponseEntity<?> showAll(@RequestParam int size, int page, String sort,  PagedResourcesAssembler<UserDto> assembler) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<UserDto> result = userService.find(pageable);
        addLink(result);
        return new ResponseEntity<>(assembler.toModel(result), HttpStatus.OK);

    }

    /**
     * Get user by part name by page and sort
     * /get-by-name
     *
     * @param size int
     * @param page      int
     * @param sort      String
     * @param name      String (findBy)
     * @return PaginationAndSort<userDto>
     */
    @GetMapping(path = "/get-by-name")
    public ResponseEntity<?> findByName(@RequestParam int size, int page, String name, String sort,  PagedResourcesAssembler<UserDto> assembler) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<UserDto> result = userService.findByName(pageable,name);
        addLink(result);
        return new ResponseEntity<>(assembler.toModel(result), HttpStatus.OK);
    }


    private void addLink(Page<UserDto> result) {
        for (UserDto u : result.getContent()){
            Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(UserRestController.class)
                            .findById(u.getId()))
                    .withRel("User " + u.getUserName());
            u.add(selfLink);
        }
    }
}

