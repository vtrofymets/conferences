package conferences.validations;

public interface Validation<T> {

    boolean validate(T t);

}
