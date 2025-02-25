package com.main.auth.controllers;

import com.main.auth.data.ClientProfile;
import com.main.auth.exeptions.AuthException;
import com.main.auth.model.Client;
import com.main.auth.model.Role;
import com.main.auth.services.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final ClientService clientService;

    public UserController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PreAuthorize("hasAnyRole('CLIENT', 'ADMIN', 'FACTORY')")
    @GetMapping("/profile")
    public ResponseEntity<ClientProfile> getProfile(@AuthenticationPrincipal UserDetails userDetails)
            throws AuthException {
        Client client = clientService.getClientByLogin(userDetails.getUsername());
        return ResponseEntity.ok(new ClientProfile(client.getLogin(),client.getPhoneNumber(), client.getEmail()));
    }

    @PreAuthorize("hasAnyRole('CLIENT', 'ADMIN', 'FACTORY')")
    @PutMapping("/profile")
    public ResponseEntity<ClientProfile> updateProfile(@AuthenticationPrincipal UserDetails userDetails,
                                                       @RequestBody Client update) throws AuthException {
        Client updatedClient = clientService.updateClient(userDetails.getUsername(), update);
        return ResponseEntity.ok(new ClientProfile(
                updatedClient.getLogin(),
                updatedClient.getPhoneNumber(),
                updatedClient.getEmail()
        ));
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("profile/admin/{login}")
    public ResponseEntity<ClientProfile> getAdminProfile(@PathVariable String login) throws AuthException {
        Client client = clientService.getClientByLogin(login);
        if (!client.getRoles().stream().map(Role::getName).toList().contains("ROLE_ADMIN")) {
            throw new AuthException("Client " + login + " is not admin.", HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(new ClientProfile(client.getLogin(), client.getPhoneNumber(), client.getEmail()));
    }

}
