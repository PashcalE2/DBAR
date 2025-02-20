package com.main.auth.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "permission")
public class Permission {

    @SequenceGenerator(name = "permission_id_gen", sequenceName = "role_id_seq", allocationSize = 1)
    @EmbeddedId
    private PermissionId id;

    @MapsId("client")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "client", nullable = false)
    private Client client;

    @MapsId("role")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role", nullable = false)
    private com.main.auth.model.Role role;

}