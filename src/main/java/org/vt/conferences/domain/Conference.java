package org.vt.conferences.domain;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class Conference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String topic;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private int participants;

    @OneToMany(mappedBy = "conference")
    private List<Talk> talks;

}
