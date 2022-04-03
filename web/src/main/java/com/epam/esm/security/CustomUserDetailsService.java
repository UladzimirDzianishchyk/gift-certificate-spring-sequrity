package com.epam.esm.security;

import com.epam.esm.User;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUserNameIgnoreCase(userName).orElseThrow(() -> new EntityNotFoundException(userName));
        return JwtUserFactory.create(user);
    }
}
