package com.epam.esm.service;

import com.epam.esm.DTO.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    OrderDTO findById(Long id);

    Page<OrderDTO> find(Pageable pageable);

    Page<OrderDTO> findOrderByUser(Long id, Pageable pageable);

    OrderDTO buyCertificate(Long userId, Long certId);
}
