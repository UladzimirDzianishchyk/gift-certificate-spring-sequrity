package com.epam.esm.resource;


import com.epam.esm.DTO.OrderDTO;
import com.epam.esm.service.OrderService;
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
@RequestMapping(path = "/orders")
public class OrderRestController {

    private final OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<OrderDTO> findById(@PathVariable long id) {
        return new ResponseEntity<>(orderService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> showAll(@RequestParam int size, int page, String sort, PagedResourcesAssembler<OrderDTO> assembler) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<OrderDTO> result = orderService.find(pageable);
        addPageLink(result);
        return new ResponseEntity<>(assembler.toModel(result), HttpStatus.OK);
    }

    @GetMapping(path = "/get-orders-from-user")
    public ResponseEntity<?> findOrderByUserId(@RequestParam int size, int page, long userId, String sort, PagedResourcesAssembler<OrderDTO> assembler){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<OrderDTO> result = orderService.findOrderByUser(userId, pageable);
        addPageLink(result);
        return new ResponseEntity<>(assembler.toModel(result), HttpStatus.OK);
    }

    /**
     * Buy certificate
     * /buy-certificate
     *
     * @param certId Long
     * @param userId Long
     * @return PaginationAndSort<orderDto>
     */
    @GetMapping(path = "/buy-certificate")
    public ResponseEntity<OrderDTO> buyCertificate(@RequestParam Long certId, Long userId) {
        return new ResponseEntity<>(orderService.buyCertificate(certId, userId), HttpStatus.OK);
    }

    private void addPageLink(Page<OrderDTO> result) {

        for (OrderDTO o : result.getContent()){
            Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(OrderRestController.class)
                            .findById(o.getId()))
                    .withRel("Order " + o.getId());
            o.add(selfLink);
        }
    }

}
