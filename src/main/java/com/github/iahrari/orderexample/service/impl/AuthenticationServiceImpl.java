package com.github.iahrari.orderexample.service.impl;

import com.github.iahrari.orderexample.config.jwt.JwtTokenUtil;
import com.github.iahrari.orderexample.dto.AuthResponse;
import com.github.iahrari.orderexample.dto.UserDTO;
import com.github.iahrari.orderexample.exception.UserAuthenticationException;

import com.github.iahrari.orderexample.service.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public AuthResponse authenticate(UserDTO userDTO) {
        try {
            Authentication authenticate = authenticationManager
                .authenticate(
                    new UsernamePasswordAuthenticationToken(
                        userDTO.getUsername(), userDTO.getPassword()
                    )
                );

            var user = (User) authenticate.getPrincipal();
            return AuthResponse.builder()
                    .token(jwtTokenUtil.generateAccessToken(user))
                    .build();
        } catch (BadCredentialsException ex) {
            throw new UserAuthenticationException("Bad credentials");
        }
    }
}
