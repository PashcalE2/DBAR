package com.main.auth.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class PermissionId implements Serializable {

    @Serial
    private static final long serialVersionUID = 1406108932523632543L;

    @Column(name = "client", nullable = false)
    private Integer client;

    @Column(name = "role", nullable = false)
    private Integer role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PermissionId entity = (PermissionId) o;
        return Objects.equals(this.role, entity.role) &&
                Objects.equals(this.client, entity.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role, client);
    }

}