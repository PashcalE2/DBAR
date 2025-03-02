package main.isbd.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "готовая_продукция")
public class Product {
    @EmbeddedId
    private ProductId id;

    @MapsId("warehouseId")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ид_склада", nullable = false)
    private ProductWarehouse warehouseId;

    @MapsId("typeId")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ид_типа", nullable = false)
    private ProductType typeId;

    @Column(name = "количество", nullable = false)
    private Integer count;

}