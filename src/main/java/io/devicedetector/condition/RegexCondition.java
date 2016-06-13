package io.devicedetector.condition;

import java.util.regex.Pattern;

public class RegexCondition implements Condition<Pattern> {
    private final Pattern pattern;

    public RegexCondition(String pattern) {
        this.pattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
    }

    @Override
    public Pattern getValue() {
        return pattern;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegexCondition)) return false;

        RegexCondition that = (RegexCondition) o;

        return pattern.equals(that.pattern);
    }

    @Override
    public int hashCode() {
        return pattern.hashCode();
    }
}
