package com.github.iahrari.orderexample.service;

import com.github.iahrari.orderexample.config.jwt.JwtTokenUtil;
import com.github.iahrari.orderexample.dto.UserDTO;
import com.github.iahrari.orderexample.exception.UserAuthenticationException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public String authenticate(UserDTO userDTO) {
        try {
            Authentication authenticate = authenticationManager
                .authenticate(
                    new UsernamePasswordAuthenticationToken(
                        userDTO.getUsername(), userDTO.getPassword()
                    )
                );

            User user = (User) authenticate.getPrincipal();
            return jwtTokenUtil.generateAccessToken(user);
        } catch (BadCredentialsException ex) {
            throw new UserAuthenticationException("Bad credentials");
        }
    }
}
