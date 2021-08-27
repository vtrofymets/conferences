package org.vt.conferences.service.validations;

/**
 * @author Vlad Trofymets
 */
@FunctionalInterface
public interface Validation<T> {

    void validate(T t) throws RuntimeException;

}
