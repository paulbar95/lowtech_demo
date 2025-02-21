package com.lowtech.webshop.util;

import java.util.Arrays;
import java.util.Objects;
import java.lang.reflect.Array;
public final class Filters {

    @SafeVarargs
    public static <T> T[] filterNotNull(T... elements) {
        return Arrays.stream(elements)
                .filter(Objects::nonNull)
                .toArray(n -> (T[]) Array.newInstance(elements.getClass().getComponentType(), n));
    }
}
/*
public final class Filters {

    @SuppressWarnings("unchecked")
    @SafeVarargs
    public static <T> T[] filterNotNull(T... elements) {
        return (T[]) Arrays.stream(elements).filter(Objects::nonNull).toArray();
    }

}*/
