package org.vt.conferences.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vt.conferences.domain.Talk;

import java.util.List;

@Repository
public interface TalksDao extends JpaRepository<Talk, Long> {

    List<Talk> findByConferenceId(final Long id);

    boolean existsByConferenceIdAndTitle(final Long id, final String title);

    List<Talk> findByConferenceIdAndSpeaker(final Long id, final String speaker);
}
