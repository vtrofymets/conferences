package org.vt.conferences.service.validations;

/**
 * @author Vlad Trofymets on 06.05.2021
 */
@FunctionalInterface
public interface Validation<T> {

    void validate(T t) throws RuntimeException;

}
