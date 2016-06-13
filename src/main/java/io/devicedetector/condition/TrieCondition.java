package io.devicedetector.condition;

public class TrieCondition implements Condition<String> {
    private final String value;

    public TrieCondition(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrieCondition)) return false;

        TrieCondition that = (TrieCondition) o;

        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
