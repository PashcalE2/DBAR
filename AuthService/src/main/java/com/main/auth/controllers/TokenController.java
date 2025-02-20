package com.main.auth.controllers;

import com.main.auth.data.JwtResponse;
import com.main.auth.data.UserLogin;
import com.main.auth.exeptions.BadCredentialsException;
import com.main.auth.exeptions.WrongParamsException;
import com.main.auth.model.Client;
import com.main.auth.services.ClientService;
import com.main.auth.services.JwtTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class TokenController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final ClientService clientService;

    public TokenController(AuthenticationManager authenticationManager, JwtTokenService jwtTokenService, ClientService clientService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.clientService = clientService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody UserLogin userLogin) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userLogin.getLogin(), userLogin.getPassword()
        ));
        return ResponseEntity.ok(new JwtResponse(
                jwtTokenService.generateToken(userLogin.getLogin(), 900000L),
                jwtTokenService.generateToken(userLogin.getLogin(), 2592000000L)
        ));
    }

    @PostMapping("/refresh/login")
    public ResponseEntity<JwtResponse> refreshLogin(@RequestBody String refresh) throws BadCredentialsException {
        String username = jwtTokenService.extractUserName(refresh);
        UserDetails userDetails = clientService.loadUserByUsername(username);
        if (jwtTokenService.verifyToken(refresh, userDetails)) {
            return ResponseEntity.ok(new JwtResponse(jwtTokenService.generateToken(username, 900000L), refresh));
        } else {
            throw new BadCredentialsException("Invalid access");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@RequestBody Client client) throws WrongParamsException {
        Client addedClient = clientService.addClient(client, "ROLE_CLIENT");
        return ResponseEntity.ok(new JwtResponse(
                jwtTokenService.generateToken(addedClient.getLogin(), 900000L),
                jwtTokenService.generateToken(addedClient.getLogin(), 2592000000L)
        ));
    }

    @PreAuthorize("hasRole('FACTORY')")
    @PostMapping("/register/admin")
    public ResponseEntity<JwtResponse> registerAdmin(@RequestBody Client client) throws WrongParamsException {
        Client addedClient = clientService.addClient(client, "ROLE_ADMIN");
        return ResponseEntity.ok(new JwtResponse(
                jwtTokenService.generateToken(addedClient.getLogin(), 900000L),
                jwtTokenService.generateToken(addedClient.getLogin(), 2592000000L)
        ));
    }

    @PreAuthorize("hasRole('SUPERVISOR')")
    @PostMapping("/register/factory")
    public ResponseEntity<JwtResponse> registerFactory(@RequestBody Client client) throws WrongParamsException {
        Client addedClient = clientService.addClient(client, "ROLE_FACTORY");
        return ResponseEntity.ok(new JwtResponse(
                jwtTokenService.generateToken(addedClient.getLogin(), 900000L),
                jwtTokenService.generateToken(addedClient.getLogin(), 2592000000L)
        ));
    }


}
