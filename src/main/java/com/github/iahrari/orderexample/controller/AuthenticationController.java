package com.github.iahrari.orderexample.controller;

import javax.validation.Valid;

import com.github.iahrari.orderexample.dto.Message;
import com.github.iahrari.orderexample.dto.UserDTO;
import com.github.iahrari.orderexample.service.AuthenticationService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ResponseHeader;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/public")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/authorize")
    @ApiResponse(
        code = 200, 
        message = "Successfully authorized",
        responseHeaders = {
            @ResponseHeader(
                name = "authorization", 
                description = "Authorization token"
            )
        }
    )
    
    public ResponseEntity<Message> authorize(@RequestBody @Valid UserDTO user) {
        var token = authenticationService.authenticate(user);
        return ResponseEntity.ok()
                    .header(
                        HttpHeaders.AUTHORIZATION,
                        token
                    ).body(new Message("Authorized"));
    }
}
