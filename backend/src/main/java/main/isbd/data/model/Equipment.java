package main.isbd.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "\"Оборудование\"")
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('\"Оборудование_ид_seq\"'")
    @Column(name = "\"ид\"", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "\"ид_цеха\"", nullable = false)
    private Workshop workshopId;

    // TODO enum?
    @Column(name = "\"состояние\"", columnDefinition = "\"Состояние_оборудования_enum\" not null")
    private String status;

    @Column(name = "\"название\"", nullable = false, length = Integer.MAX_VALUE)
    private String name;

    @Column(name = "\"описание\"", length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "\"дата_выпуска\"", nullable = false)
    private LocalDate releasedAt;
}