package main.isbd.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "\"Адрес_службы_поддержки\"")
public class SupportServiceAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"ид_службы_поддержки\"", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "\"ид_службы_поддержки\"", nullable = false)
    private SupportService supportServiceId;

    @Column(name = "\"адрес\"", nullable = false, length = Integer.MAX_VALUE)
    private String address;

}