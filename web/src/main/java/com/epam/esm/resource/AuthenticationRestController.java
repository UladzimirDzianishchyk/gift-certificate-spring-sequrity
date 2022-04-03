package com.epam.esm.resource;

import com.epam.esm.DTO.AuthRegRequestDto;
import com.epam.esm.User;
import com.epam.esm.security.JwtTokenProvider;
import com.epam.esm.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/auth")
public class AuthenticationRestController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    public AuthenticationRestController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRegRequestDto requestDto) {

        String userName = requestDto.getUserName();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, requestDto.getPassword()));
        User user = userService.findOneByName(userName);

        String token = jwtTokenProvider.createToken(userName, user.getRoles());

        Map<Object, Object> response = new HashMap<>();
        response.put("userName", userName);
        response.put("token", token);

        return ResponseEntity.ok(response);

    }


    @PostMapping("/reg")
    public ResponseEntity<?> newUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.save(user));
    }

//    @PutMapping("/add-admin")
//    public ResponseEntity<?> addAdmin(Long id) {
//        return ResponseEntity.ok(userService.addAdmin(id));
//    }
}
