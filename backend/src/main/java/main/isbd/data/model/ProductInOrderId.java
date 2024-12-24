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
public class ProductInOrderId implements java.io.Serializable {
    private static final long serialVersionUID = -6959250931304743409L;

    @Column(name = "\"ид_заказа\"", nullable = false)
    private Integer orderId;

    @Column(name = "\"ид_типа\"", nullable = false)
    private Integer typeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ProductInOrderId entity = (ProductInOrderId) o;
        return Objects.equals(this.orderId, entity.orderId) &&
                Objects.equals(this.typeId, entity.typeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, typeId);
    }

}