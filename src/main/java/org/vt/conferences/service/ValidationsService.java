package org.vt.conferences.service;

import lombok.NonNull;
import org.vt.conferences.service.validations.Validation;

import java.util.List;

/**
 * @author Vlad Trofymets
 */
public interface ValidationsService {

    void validation(@NonNull Object o);

   <T> void validation(@NonNull T t, @NonNull List<Validation<T>> validations);

}
