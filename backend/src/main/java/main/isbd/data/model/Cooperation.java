package main.isbd.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import main.isbd.data.users.Factory;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "\"Сотрудничество\"")
public class Cooperation {
    @EmbeddedId
    private CooperationId id;

    @MapsId("factoryId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "\"ид_завода\"", nullable = false)
    private Factory factoryId;

    @MapsId("organizationId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "\"ид_организации\"", nullable = false)
    private Organization organizationId;

    @Column(name = "\"поступило\"", nullable = false)
    private Instant sentAt;

    @Column(name = "\"завершено\"")
    private Instant completedAt;

    // TODO enum?
    @Column(name = "\"форма\"", columnDefinition = "\"Форма_сотрудничества_enum\" not null")
    private String type;

    // TODO enum?
    @Column(name = "\"состояние\"", columnDefinition = "\"Состояние_сотрудничества_enum\" not null")
    private String status;
}