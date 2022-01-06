package com.github.iahrari.orderexample.service;

import com.github.iahrari.orderexample.dto.AuthResponse;
import com.github.iahrari.orderexample.dto.UserDTO;

public interface AuthenticationService {
    AuthResponse authenticate(UserDTO userDTO);
}
