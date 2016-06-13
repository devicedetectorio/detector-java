package io.devicedetector.feature;

public class StringBetween implements Feature {
    private final String name;
    private final String open;
    private final String close;

    public StringBetween(String name, String open, String close) {
        this.name = name;
        this.open = open;
        this.close = close;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getOpen() {
        return open;
    }

    public String getClose() {
        return close;
    }
}
