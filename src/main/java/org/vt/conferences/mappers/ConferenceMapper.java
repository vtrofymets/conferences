package org.vt.conferences.mappers;

import conferences.api.dto.ConferenceCreatedResponse;
import conferences.api.dto.ConferenceRequest;
import conferences.api.dto.ConferenceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.vt.conferences.domain.Conference;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConferenceMapper {

    Conference map(ConferenceRequest request);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "topic", source = "request.topic")
    @Mapping(target = "dateStart", source = "request.dateStart")
    @Mapping(target = "dateEnd", source = "request.dateEnd")
    @Mapping(target = "participants", source = "request.participants")
    Conference map(Long id, ConferenceRequest request);

    ConferenceCreatedResponse mapCreatedConferenceResponse(Conference conference);

    ConferenceResponse mapToConferenceResponse(Conference conference);

    List<ConferenceResponse> mapList(List<Conference> conference);

}
