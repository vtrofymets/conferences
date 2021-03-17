package conferences.dao;

import conferences.domain.Conference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ConferenceDao extends JpaRepository<Conference, Integer> {
    Optional<Conference> findByName(final String name);

    @Query("select count(*) from Conference c " +
            "where " +
            "(c.dateStart between :start and :end) " +
            "or " +
            "(c.dateEnd between :start and :end)")
    int checkOnExistPeriod(@Param("start") final LocalDate start, @Param("end") final LocalDate end);

    @Query("select c from Conference c where c.id = :conferenceId and c.dateStart < 30")
    boolean overdueTopic(@Param("conferenceId") final Integer id, @Param("end") final LocalDate end);

}
