package com.github.iahrari.orderexample.service;

import com.github.iahrari.orderexample.dto.UserDTO;

public interface AuthenticationService {
    String authenticate(UserDTO userDTO);
}
