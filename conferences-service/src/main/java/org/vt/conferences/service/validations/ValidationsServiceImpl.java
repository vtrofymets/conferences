package org.vt.conferences.service.validations;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.vt.conferences.utils.GenericUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Vlad Trofymets
 */
@Service
@Slf4j
public class ValidationsServiceImpl implements ValidationsService {

    private final Map<String, List<Validation<?>>> validators;

    public ValidationsServiceImpl(List<Validation<?>> validators) {
        this.validators = validators.stream()
                .collect(Collectors.groupingBy(GenericUtils::extractGeneric));
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public <T> void validation(@NonNull T model) {
        log.info("Start validation for=[{}]", model);
        var modelValidators = Optional.ofNullable(validators.get(model.getClass()
                        .getName()))
                .map(validations -> validations.stream()
                        .map(x -> (Validation<T>) x)
                        .toList())
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown validation for type=[" + model.getClass() + "]"));
        log.info("Get modelValidators=[{}]", modelValidators.size());
        validation(model, modelValidators);
    }

}
