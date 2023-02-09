package com.boutiqaat.catalogadminexportimportplus.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender {

    MEN("2741"), WOMEN("4194"), ALL("2741,4194");

    private String code;

    private static final Map<String,Gender> LOOK_UP = new HashMap<>();

    static{
        Stream.of(Gender.values()).forEach(p -> LOOK_UP.put(p.code, p));
    }

    public static Gender of(String code) {
        if(code.equals("4194,2741"))
            return Gender.ALL;
        return LOOK_UP.get(code.trim());
    }

    public static String of(List<String> codes) {
        Set<String> set = new HashSet<>(codes);
        if(set.size() == 2)
            return Gender.ALL.name();
        Gender g = Gender.of(codes.get(0));
        if(g != null)
            return g.name();
        return null;
    }
}
