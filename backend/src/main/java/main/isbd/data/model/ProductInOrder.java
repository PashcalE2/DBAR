package main.isbd.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "\"Продукция_в_заказе\"")
public class ProductInOrder {
    @EmbeddedId
    private ProductInOrderId id;

    @MapsId("orderId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "\"ид_заказа\"", nullable = false)
    private Order orderId;

    @MapsId("typeId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "\"ид_типа\"", nullable = false)
    private ProductType typeId;

    @Column(name = "\"количество\"", nullable = false)
    private Integer count;

    // TODO enum?
    @Column(name = "\"статус\"", columnDefinition = "\"Статус_продукции_в_заказе_enum\" not null")
    private Object status;
}