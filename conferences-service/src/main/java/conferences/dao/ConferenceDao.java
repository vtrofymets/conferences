package conferences.dao;

import conferences.domain.Conference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConferenceDao extends JpaRepository<Conference, Integer> {
    Optional<Conference> findByName(final String name);

    boolean existBetweenDateStartAndDateEnd(final String start, final String end);
}
