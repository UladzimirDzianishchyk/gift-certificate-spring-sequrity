package com.epam.esm.service;

import com.epam.esm.DTO.OrderDTO;
import com.epam.esm.GiftCertificate;
import com.epam.esm.Order;
import com.epam.esm.User;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.serviceimpl.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderServiceTest {

    OrderRepository orderRepositoryMoc = Mockito.mock(OrderRepository.class);
    UserRepository userRepositoryMoc = Mockito.mock(UserRepository.class);
    GiftCertificateRepository giftCertificateRepositoryMoc = Mockito.mock(GiftCertificateRepository.class);

    Order order = new Order();
    OrderDTO orderDTO = new OrderDTO();
    List<Order> orders = Arrays.asList(order);
    GiftCertificate giftCertificate = new GiftCertificate();
    User user = new User();
    Pageable pageable = PageRequest.of(1, 2);
    Page<Order> page = new PageImpl<>(orders, pageable, 2);

    OrderService orderService = new OrderServiceImpl(orderRepositoryMoc, userRepositoryMoc, giftCertificateRepositoryMoc);

    @Test
    void findById() {
        order.setId(1L);
        orderDTO.setId(1L);
        Mockito.when(orderRepositoryMoc.findById(1L)).thenReturn(Optional.of(order));
        assertEquals(orderService.findById(1L).getId(),orderDTO.getId());
        assertThrows(EntityNotFoundException.class, () -> orderService.findById(5L));
    }

    @Test
    void find() {
        Mockito.when(orderRepositoryMoc.findAll(pageable)).thenReturn(page);
        assertEquals(orderService.find(pageable).getContent().size(),1);
    }

//    @Test
//    void findOrderByUser() {
//        Mockito.when(orderRepositoryMoc.findOrdersByUser_Id(1L, pageable)).thenReturn(page);
//        assertEquals(orderService.findOrderByUser(pageable, 1L).getContent().size(), 1);
//        Mockito.when(orderRepositoryMoc.findOrdersByUser_Id(5L, pageable)).thenReturn(Page.empty());
//        assertThrows(EntityNotFoundException.class, () -> orderService.findOrderByUser(pageable,5L));
//    }

    @Test
    void buyCertificate() {
        giftCertificate.setPrice(2D);
        giftCertificate.setId(1L);
        user.setId(1L);
        order.setId(1L);
        Mockito.when(giftCertificateRepositoryMoc.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> orderService.buyCertificate(1L, 1L));
        Mockito.when(giftCertificateRepositoryMoc.findById(1L)).thenReturn(Optional.of(giftCertificate));
        Mockito.when(userRepositoryMoc.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> orderService.buyCertificate(1L, 1L));
    }
}