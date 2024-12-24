package main.isbd.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "\"Расписание_консультантов\"")
public class AdminSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('\"Расписание_консультантов_ид_seq\"'")
    @Column(name = "\"ид\"", nullable = false)
    private Integer id;

    @Column(name = "\"рабочее_время\"", nullable = false)
    private Integer workingHours;

    @Column(name = "\"описание\"", length = Integer.MAX_VALUE)
    private String description;

}