package main.isbd.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "\"Адрес_склада_готовой_продукции\"")
public class ProductWarehouseAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"ид_склада\"", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "\"ид_склада\"", nullable = false)
    private ProductWarehouse warehouseId;

    @Column(name = "\"адрес\"", nullable = false, length = Integer.MAX_VALUE)
    private String address;

}