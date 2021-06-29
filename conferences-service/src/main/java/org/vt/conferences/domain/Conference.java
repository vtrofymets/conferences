package org.vt.conferences.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Objects;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Conference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String topic;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private int participants;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conference that = (Conference) o;
        return participants == that.participants && Objects.equals(id, that.id) && Objects.equals(name,
                that.name) && Objects.equals(topic, that.topic) && Objects.equals(dateStart,
                that.dateStart) && Objects.equals(dateEnd, that.dateEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, topic, dateStart, dateEnd, participants);
    }

    @Override
    public String toString() {
        return "Conference{name='" + name + '\'' + ", topic='" + topic + '\'' + ", dateStart=" + dateStart + ", dateEnd=" + dateEnd + ", participants=" + participants + '}';
    }
}
