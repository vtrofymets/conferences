package conferences.dao;

import conferences.domain.ConferenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConferenceDao extends JpaRepository<ConferenceEntity, Integer> {
}
