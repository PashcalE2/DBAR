package main.isbd.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "\"Материалы\"")
public class Material {
    @EmbeddedId
    private MaterialId id;

    @MapsId("warehouseId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "\"ид_склада\"", nullable = false)
    private MaterialWarehouse warehouseId;

    @MapsId("typeId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "\"ид_типа\"", nullable = false)
    private MaterialType typeId;

    @Column(name = "\"количество\"", nullable = false)
    private Integer count;

}