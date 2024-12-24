package main.isbd.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "\"Описание_продукции\"")
public class ProductDescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"ид_типа\"", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "\"ид_типа\"", nullable = false)
    private ProductType typeId;

    @Column(name = "\"описание\"", nullable = false, length = Integer.MAX_VALUE)
    private String description;

}