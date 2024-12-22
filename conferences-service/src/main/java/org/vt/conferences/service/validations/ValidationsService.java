package org.vt.conferences.service.validations;

import lombok.NonNull;

import java.util.List;

/**
 * @author Vlad Trofymets
 */
public interface ValidationsService {

    <T> void validation(@NonNull T model);

    default <T> void validation(@NonNull T model, @NonNull List<Validation<T>> validations) {
        validations.forEach(v -> v.validate(model));
    }

}
