package org.vt.conferences.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;


@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode(of = {"id"})
public class Conference {

    @Id
    @SequenceGenerator(name = "conf_seq", sequenceName = "conf_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "conf_seq")
    private Long id;
    private String name;
    private String topic;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private int participants;

    @OneToMany(mappedBy = "conference")
    private List<Talk> talks;

}
