package main.isbd.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import main.isbd.data.users.Factory;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "\"Склад_готовой_продукции\"")
public class ProductWarehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('\"Склад_готовой_продукции_ид_seq\"'")
    @Column(name = "\"ид\"", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"ид_завода\"")
    private Factory factoryId;

}