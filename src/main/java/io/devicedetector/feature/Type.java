package io.devicedetector.feature;

import io.devicedetector.exception.Exception;

public enum Type {
    substringBetween("substringBetween");

    private final String name;

    Type(String name) {
        this.name = name;
    }

    public static Type create(String name) throws Exception {
        for (Type allowedType : Type.values()) {
            if (allowedType.name.equals(name)) {
                return allowedType;
            }
        }

        throw new Exception(String.format("Unknown type \"%s\".", name));
    }
}
