package com.epam.esm.serviceimpl;

import com.epam.esm.DTO.UserDto;
import com.epam.esm.User;
import com.epam.esm.exception.EntityIsExistException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.RoleRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import com.epam.esm.utils.ObjectMapperUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto save(User user) {
        user.getRoles().clear();
        user.getRoles().add(roleRepository.findByNameIgnoreCase("user"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        return ObjectMapperUtils.map(userRepository.save(user), UserDto.class);
    }

    @Override
    public UserDto findById(Long id) {
        return ObjectMapperUtils.map(userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User" + id)), UserDto.class);
    }

    @Override
    public Page<UserDto> find(Pageable pageable) {
        return ObjectMapperUtils.mapEntityPageIntoDtoPage(userRepository
                .findAll(pageable), UserDto.class);
    }

    @Override
    public Page<UserDto> findByName(Pageable pageable, String name) {
        return ObjectMapperUtils.mapEntityPageIntoDtoPage(userRepository.findByUserNameContainingIgnoreCase(pageable, name), UserDto.class);
    }

    @Override
    public User findOneByName(String name) {
        return userRepository.findByUserNameIgnoreCase(name).orElseThrow(() ->
                new EntityNotFoundException(name));
    }

    @Override
    public UserDto addAdmin(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("User" + id));
        user.getRoles().add(roleRepository.findByNameIgnoreCase("admin"));
        return ObjectMapperUtils.map(userRepository.save(user),UserDto.class);
    }
}
