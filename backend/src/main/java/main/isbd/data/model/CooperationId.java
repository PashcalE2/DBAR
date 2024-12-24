package main.isbd.data.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class CooperationId implements java.io.Serializable {
    private static final long serialVersionUID = -6075105752456044926L;
    @Column(name = "\"ид_завода\"", nullable = false)
    private Integer factoryId;

    @Column(name = "\"ид_организации\"", nullable = false)
    private Integer organizationId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CooperationId entity = (CooperationId) o;
        return Objects.equals(this.factoryId, entity.factoryId) &&
                Objects.equals(this.organizationId, entity.organizationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(factoryId, organizationId);
    }

}