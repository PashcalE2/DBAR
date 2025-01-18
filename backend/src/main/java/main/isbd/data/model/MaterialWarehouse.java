package main.isbd.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "\"Склад_сырья\"")
public class MaterialWarehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('\"Склад_сырья_ид_seq\"'")
    @Column(name = "\"ид\"", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"ид_завода\"")
    private Factory factoryId;

    @Column(name = "\"адрес\"", nullable = false, length = Integer.MAX_VALUE)
    private String address;
}