package com.boutiqaat.catalogadminexportimportplus.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Visibility {

    HIDDEN(1), CATALOG(2), SEARCH(3), ALL(4);

    private static final Map<Integer, Visibility> LOOK_UP = new HashMap<>(5, 1.0F);

    static {
        Arrays.stream(Visibility.values()).forEach(v -> LOOK_UP.put(v.value, v));
    }
    private int value;

    public static Visibility of(int val) {
        return LOOK_UP.get(val);
    }
}
