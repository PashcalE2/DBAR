package main.isbd.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "\"Название_продукции\"")
public class ProductName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"ид_типа\"", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "\"ид_типа\"", nullable = false)
    private ProductType typeId;

    @Column(name = "\"название\"", nullable = false, length = Integer.MAX_VALUE)
    private String name;

}