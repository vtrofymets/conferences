package org.vt.conferences.utils;

import lombok.experimental.UtilityClass;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.stream.Stream;

@UtilityClass
public class GenericUtils {

    public static String extractGeneric(Object t) {
        var genericInterfaces = (ParameterizedType) findTypeOrThrowException(t.getClass()
                .getGenericInterfaces());
        return findTypeOrThrowException(genericInterfaces.getActualTypeArguments()).getTypeName();
    }

    public static Type findTypeOrThrowException(Type[] types) {
        return Stream.of(types)
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
