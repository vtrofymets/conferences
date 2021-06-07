package org.vt.conferences.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vt.conferences.domain.Conference;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConferenceDao extends JpaRepository<Conference, Integer> {

    Optional<Conference> findByName(final String name);

    @Query("select count(*) from Conference c " + "where c.name != :name and" + "(c.dateStart between :start and :end " + "or " + "c.dateEnd between :start and :end)")
    int checkOnExistPeriod(@Param("name") String name, @Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("select c from Conference c " + "where c.dateStart >= CURRENT_DATE()")
    List<Conference> findAllActive();

}
