package org.vt.conferences.domain;

import conferences.api.dto.TalkType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id"})
public class Talk {

    @Id
    @SequenceGenerator(name = "talk_seq", sequenceName = "talk_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "talk_seq")
    private Long id;
    private Long conferenceId;
    private String title;
    private String description;
    private String speaker;
    @Enumerated(EnumType.STRING)
    private TalkType type;

    @ManyToOne
    @JoinColumn(name = "conferenceId", referencedColumnName = "id", insertable = false, updatable = false)
    private Conference conference;

}
