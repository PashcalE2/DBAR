package main.isbd.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "тип_материала")
public class MaterialType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('тип_материала_ид_seq'")
    @Column(name = "ид", nullable = false)
    private Integer id;

    @Column(name = "цена", nullable = false)
    private Float price;

    @Column(name = "название", length = Integer.MAX_VALUE)
    private String name;

    @Column(name = "описание", length = Integer.MAX_VALUE)
    private String description;
}