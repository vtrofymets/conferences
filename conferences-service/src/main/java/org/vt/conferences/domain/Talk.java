package org.vt.conferences.domain;

import conferences.api.dto.TalkType;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class Talk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long conferenceId;
    private String title;
    private String description;
    private String speaker;
    @Enumerated(EnumType.STRING)
    private TalkType type;

}
