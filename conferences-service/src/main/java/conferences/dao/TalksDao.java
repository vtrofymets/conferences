package conferences.dao;

import conferences.domain.Talk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TalksDao extends JpaRepository<Talk, Integer> {

    List<Talk> findByConferenceId(final Integer id);

    boolean existsByConferenceIdAndTitle(final Integer id, final String title);

    List<Talk> findByConferenceIdAndSpeaker(final Integer id, final String speaker);
}
