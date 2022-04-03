package com.epam.esm.service;

import com.epam.esm.DTO.UserDto;
import com.epam.esm.User;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.RoleRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.serviceimpl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserServiceTest {



    UserRepository userRepositoryMoc = Mockito.mock(UserRepository.class);
    RoleRepository roleRepositoryMoc = Mockito.mock(RoleRepository.class);
    BCryptPasswordEncoder passwordEncoder = Mockito.mock(BCryptPasswordEncoder.class);

    User user = new User();
    UserDto userDto = new UserDto();
    List<User> users = new ArrayList<>();
    List<UserDto> dtos = new ArrayList<>();

    Pageable pageable = PageRequest.of(1, 2);
    Page<User> page = new PageImpl<>(users, pageable, 2);
    Page<UserDto> pageDto = new PageImpl<>(dtos, pageable, 2);

    UserService userService = new UserServiceImpl(userRepositoryMoc, roleRepositoryMoc, passwordEncoder);


    @Test
    void findById() {
        user.setId(1L);
        userDto.setId(1L);
        Mockito.when(userRepositoryMoc.findById(1L)).thenReturn(Optional.of(user));
        assertEquals(userDto.getId(), userService.findById(1L).getId());
        assertThrows(EntityNotFoundException.class,() -> userService.findById(5L));
    }

    @Test
    void find() {
        Mockito.when(userRepositoryMoc.findAll(pageable)).thenReturn(page);
        assertEquals(userRepositoryMoc.findAll(pageable).getContent(),page.getContent());
    }

    @Test
    void findByName() {
        Mockito.when(userRepositoryMoc.findByUserNameContainingIgnoreCase(pageable, "te"))
                .thenReturn(page);
        assertEquals(userService.findByName(pageable, "te").getContent(),pageDto.getContent());
    }
}