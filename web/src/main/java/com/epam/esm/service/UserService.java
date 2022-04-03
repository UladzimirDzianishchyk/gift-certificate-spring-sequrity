package com.epam.esm.service;

import com.epam.esm.DTO.UserDto;
import com.epam.esm.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserDto save(User user);

    UserDto findById(Long id);

    Page<UserDto> find(Pageable pageable);

    Page<UserDto> findByName(Pageable pageable, String name);

    User findOneByName(String name);

    UserDto addAdmin(Long id);
}
