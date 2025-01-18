package main.isbd.data.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class MaterialId implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = -8578646759939182285L;

    @Column(name = "\"ид_склада\"", nullable = false)
    private Integer warehouseId;

    @Column(name = "\"ид_типа\"", nullable = false)
    private Integer typeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MaterialId entity = (MaterialId) o;
        return Objects.equals(this.warehouseId, entity.warehouseId) &&
                Objects.equals(this.typeId, entity.typeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(warehouseId, typeId);
    }

}