package conferences.dao;

import conferences.domain.ConferenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConferenceDao extends JpaRepository<ConferenceEntity, Integer> {
    Optional<ConferenceEntity> findByConferenceName(final String name);
}
