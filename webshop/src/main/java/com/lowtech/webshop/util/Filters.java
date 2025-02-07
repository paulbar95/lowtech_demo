package com.lowtech.webshop.util;

import java.util.Arrays;
import java.util.Objects;

public final class Filters {

    @SuppressWarnings("unchecked")
    @SafeVarargs
    public static <T> T[] filterNotNull(T... elements) {
        return (T[]) Arrays.stream(elements).filter(Objects::nonNull).toArray();
    }

}
