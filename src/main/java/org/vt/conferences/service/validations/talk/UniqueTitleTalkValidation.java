package org.vt.conferences.service.validations.talk;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.vt.conferences.dao.TalksDao;
import org.vt.conferences.domain.Talk;
import org.vt.conferences.exceptions.TalkException;
import org.vt.conferences.service.validations.Validation;

/**
 * @author Vlad Trofymets on 06.05.2021
 */
@Service
@RequiredArgsConstructor
public class UniqueTitleTalkValidation implements Validation<Talk> {

    private final TalksDao talksDao;

    @Override
    public void validate(Talk talk) {
        if (talksDao.existsByConferenceIdAndTitle(talk.getConferenceId(), talk.getTitle())) {
            throw new TalkException("Title already exist!", HttpStatus.CONFLICT);
        }
    }
}