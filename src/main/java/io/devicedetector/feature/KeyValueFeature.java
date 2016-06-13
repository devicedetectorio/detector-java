package io.devicedetector.feature;

public class KeyValueFeature implements Feature {
    private final String name;
    private final Object value;

    public KeyValueFeature(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }


}
