package com.github.iahrari.orderexample.service;

import com.github.iahrari.orderexample.dto.AuthResponse;
import com.github.iahrari.orderexample.dto.AuthenticationUserDTO;

public interface AuthenticationService {
    AuthResponse authenticate(AuthenticationUserDTO userDTO);
}
