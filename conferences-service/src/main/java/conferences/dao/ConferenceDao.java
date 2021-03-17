package conferences.dao;

import conferences.domain.Conference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConferenceDao extends JpaRepository<Conference, Integer> {
    Optional<Conference> findByName(final String name);

    @Query("select c from Conference c " +
            "where " +
            "(c.dateStart between :start and :end) " +
            "or " +
            "(c.dateEnd between :start and :end)")
    Optional<Conference> existDates(@Param("start") final String start, @Param("end") final String end);
}
