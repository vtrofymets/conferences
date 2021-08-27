package org.vt.conferences.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.vt.conferences.service.validations.Validation;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Vlad Trofymets
 */
@Service
@Slf4j
public class ValidationsServiceImpl implements ValidationsService {

    private final Map<String, List<Validation>> validations;

    public ValidationsServiceImpl(List<Validation<?>> validations) {
        this.validations = validations.stream()
                .collect(Collectors.groupingBy(this::extractGeneric));
    }

    private String extractGeneric(Object t) {
        var genericInterfaces = (ParameterizedType) findTypeOrThrowException(t.getClass()
                .getGenericInterfaces());
        return findTypeOrThrowException(genericInterfaces.getActualTypeArguments()).getTypeName();
    }

    private Type findTypeOrThrowException(Type[] types) {
        return Stream.of(types)
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public void validation(@NonNull Object o) {
        validations.get(o.getClass()
                .getName())
                .forEach(v -> v.validate(o));
    }

    @Override
    public <T> void validation(@NonNull T t, @NonNull List<Validation<T>> validations) {
        log.info("Start validation for=[{}]", t);
        validations.forEach(v -> v.validate(t));
    }
}
