package org.vt.conferences.mappers;

import conferences.api.dto.TalkRequest;
import conferences.api.dto.TalkResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.vt.conferences.domain.Talk;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TalkMapper {

    @Mapping(target = "conferenceId", source = "confId")
    @Mapping(target = "title", source = "request.title")
    @Mapping(target = "description", source = "request.description")
    @Mapping(target = "speaker", source = "request.speaker")
    @Mapping(target = "type", source = "request.talkType")
    Talk map(Long confId, TalkRequest request);

    TalkResponse map(Talk talk);

    List<TalkResponse> mapList(List<Talk> talks);
}
