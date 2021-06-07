package org.vt.conferences.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vt.conferences.domain.Talk;

import java.util.List;

@Repository
public interface TalksDao extends JpaRepository<Talk, Integer> {

    List<Talk> findByConferenceId(final Integer id);

    boolean existsByConferenceIdAndTitle(final Integer id, final String title);

    List<Talk> findByConferenceIdAndSpeaker(final Integer id, final String speaker);
}
