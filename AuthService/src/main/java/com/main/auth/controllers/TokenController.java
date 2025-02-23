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
import org.springframework.web.bind.annotation.*;

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

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Hello World");
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody UserLogin userLogin) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userLogin.getLogin(), userLogin.getPassword()
        ));
        return ResponseEntity.ok(new JwtResponse(
                jwtTokenService.generateAccessToken(userLogin.getLogin(), "ROLE_CLIENT"),
                jwtTokenService.generateRefreshToken(userLogin.getLogin(), "ROLE_CLIENT")
        ));
    }

    @PostMapping("/login/admin")
    public ResponseEntity<JwtResponse> loginAdmin(@RequestBody UserLogin userLogin) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userLogin.getLogin(), userLogin.getPassword()
        ));
        return ResponseEntity.ok(new JwtResponse(
                jwtTokenService.generateAccessToken(userLogin.getLogin(), "ROLE_ADMIN"),
                jwtTokenService.generateRefreshToken(userLogin.getLogin(), "ROLE_ADMIN")
        ));
    }

    @PostMapping("/login/factory")
    public ResponseEntity<JwtResponse> loginFactory(@RequestBody UserLogin userLogin) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userLogin.getLogin(), userLogin.getPassword()
        ));
        return ResponseEntity.ok(new JwtResponse(
                jwtTokenService.generateAccessToken(userLogin.getLogin(), "ROLE_FACTORY"),
                jwtTokenService.generateRefreshToken(userLogin.getLogin(), "ROLE_FACTORY")
        ));
    }

    @PostMapping("/refresh/login")
    public ResponseEntity<String> refreshLogin(@RequestBody String refresh) throws BadCredentialsException {
        return ResponseEntity.ok(jwtTokenService.generateAccessToken(refresh));
    }

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@RequestBody Client client) throws WrongParamsException {
        Client addedClient = clientService.addClient(client, "ROLE_CLIENT");
        return ResponseEntity.ok(new JwtResponse(
                jwtTokenService.generateAccessToken(addedClient.getLogin(), "ROLE_CLIENT"),
                jwtTokenService.generateRefreshToken(addedClient.getLogin(), "ROLE_CLIENT")
        ));
    }

    @PreAuthorize("hasRole('FACTORY')")
    @PostMapping("/register/admin")
    public ResponseEntity<JwtResponse> registerAdmin(@RequestBody Client client) throws WrongParamsException {
        Client addedClient = clientService.addClient(client, "ROLE_ADMIN");
        return ResponseEntity.ok(new JwtResponse(
                jwtTokenService.generateAccessToken(addedClient.getLogin(), "ROLE_ADMIN"),
                jwtTokenService.generateRefreshToken(addedClient.getLogin(), "ROLE_ADMIN")
        ));
    }

    @PreAuthorize("hasRole('SUPERVISOR')")
    @PostMapping("/register/factory")
    public ResponseEntity<JwtResponse> registerFactory(@RequestBody Client client) throws WrongParamsException {
        Client addedClient = clientService.addClient(client, "ROLE_FACTORY");
        return ResponseEntity.ok(new JwtResponse(
                jwtTokenService.generateAccessToken(addedClient.getLogin(), "ROLE_FACTORY"),
                jwtTokenService.generateRefreshToken(addedClient.getLogin(), "ROLE_FACTORY")
        ));
    }

}
