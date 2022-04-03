package com.epam.esm.serviceimpl;

import com.epam.esm.DTO.OrderDTO;
import com.epam.esm.GiftCertificate;
import com.epam.esm.Order;
import com.epam.esm.Status;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.OrderService;
import com.epam.esm.utils.ObjectMapperUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final GiftCertificateRepository giftCertificateRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, GiftCertificateRepository giftCertificateRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.giftCertificateRepository = giftCertificateRepository;
    }

    @Override
    public OrderDTO findById(Long id) {
        return ObjectMapperUtils.map(orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order " + id)), OrderDTO.class);
    }

    @Override
    public Page<OrderDTO> find(Pageable pageable) {
        return ObjectMapperUtils.mapEntityPageIntoDtoPage(orderRepository
                .findAll(pageable), OrderDTO.class);
    }


    @Override
    public Page<OrderDTO> findOrderByUser(Long id, Pageable pageable) {
        Page<Order> result = orderRepository.findAllByUserId(id, pageable);
        if (!result.hasContent()) throw new EntityNotFoundException("User " + id);
        return ObjectMapperUtils.mapEntityPageIntoDtoPage(result, OrderDTO.class);
    }

    @Transactional
    @Override
    public OrderDTO buyCertificate(Long userId, Long certId) {
        Order order = new Order();
        GiftCertificate gc = giftCertificateRepository.findById(certId)
                .orElseThrow(() -> new EntityNotFoundException("Certificate " + certId));
        order.setUser(userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User " + userId)));
        order.setPrice(gc.getPrice());
        order.setStatus(Status.NEW);
        order.setCertificate(gc);
        return ObjectMapperUtils.map(orderRepository.save(order),OrderDTO.class);
    }
}
