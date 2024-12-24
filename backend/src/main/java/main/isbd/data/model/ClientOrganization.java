package main.isbd.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import main.isbd.data.users.Client;

@Getter
@Setter
@Entity
@Table(name = "\"Организация_клиента\"")
public class ClientOrganization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"ид_клиента\"", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "\"ид_клиента\"", nullable = false)
    private Client clientId;

    // TODO enum?
    @Column(name = "\"название\"", columnDefinition = "\"Доступные_организации_enum\" not null")
    private String name;
}