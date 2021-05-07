package conferences.service.validations;

/**
 * @author Vlad Trofymets on 06.05.2021
 */
public interface Validation<T> {

    void validate(T t) throws RuntimeException;

}
