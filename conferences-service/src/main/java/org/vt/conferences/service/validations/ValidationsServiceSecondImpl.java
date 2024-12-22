package org.vt.conferences.service.validations;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.vt.conferences.utils.GenericUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

//@Service
@Slf4j
public class ValidationsServiceSecondImpl implements ValidationsService {

    private final Map<String, List<Validation>> validators;

    public ValidationsServiceSecondImpl(List<Validation<?>> validators) {
        this.validators = validators.stream()
                .collect(Collectors.groupingBy(GenericUtils::extractGeneric));
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public <T> void validation(@NonNull T model) {
        log.info("Start validation for=[{}]", model);
        Optional.ofNullable(validators.get(model.getClass()
                        .getName()))
                .ifPresentOrElse(validations -> validations.forEach(v -> v.validate(model)), () -> {
                    throw new IllegalArgumentException("Unknown validation for type=[" + model.getClass() + "]");
                });
    }
}
