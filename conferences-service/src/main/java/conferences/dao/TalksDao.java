package conferences.dao;

import conferences.domain.TalkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TalksDao extends JpaRepository<TalkEntity, Integer> {

    List<TalkEntity> findByConferenceId(final Integer id);
}
