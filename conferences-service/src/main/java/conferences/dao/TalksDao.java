package conferences.dao;

import conferences.domain.TalkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TalksDao extends JpaRepository<TalkEntity, Integer> {
}
