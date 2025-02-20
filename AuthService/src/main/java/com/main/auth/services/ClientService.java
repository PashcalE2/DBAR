package com.main.auth.services;

import com.main.auth.exeptions.WrongParamsException;
import com.main.auth.model.Client;
import com.main.auth.model.Permission;
import com.main.auth.model.PermissionId;
import com.main.auth.model.Role;
import com.main.auth.repositories.ClientRepository;
import com.main.auth.repositories.PermissionRepository;
import com.main.auth.repositories.RoleRepository;
import com.main.auth.security.ClientDetails;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService implements UserDetailsService {

    private final PasswordService passwordService;
    private final ClientRepository clientRepository;
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;

    public ClientService(PasswordService passwordService, ClientRepository clientRepository,
                         PermissionRepository permissionRepository, RoleRepository roleRepository) {
        this.passwordService = passwordService;
        this.clientRepository = clientRepository;
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
    }

    public Client addClient(Client client, String roleName) throws WrongParamsException {
        client.setPassword(passwordService.makeBCryptHash(client.getPassword()));
        Client savedClient = clientRepository.save(client);
        Role userRole = roleRepository.findByName(roleName)
                .orElseThrow(() -> new WrongParamsException("No role " + roleName, HttpStatus.NOT_FOUND));
        Permission permission = new Permission();
        PermissionId permissionId = new PermissionId();
        permissionId.setRole(userRole.getId());
        permissionId.setClient(savedClient.getId());
        permission.setId(permissionId);
        permission.setRole(userRole);
        permission.setClient(savedClient);
        permissionRepository.save(permission);
        return savedClient;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Client> clientDetail = clientRepository.findByLogin(username);
        return clientDetail.map(ClientDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User with login " + username + " not found"));
    }

}
