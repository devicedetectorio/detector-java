package io.devicedetector.configuration.reader.stream;

import io.devicedetector.condition.Condition;
import io.devicedetector.condition.RegexCondition;
import io.devicedetector.condition.TrieCondition;
import io.devicedetector.condition.Type;
import io.devicedetector.exception.Exception;
import org.slieb.throwables.FunctionWithThrowable;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public class ConditionsFlatMapFactory {
    public static FunctionWithThrowable<Map<String, Object>, Stream<Condition>, Exception> create() {
        return (config) -> {
            final Type type = Type.create((String) config.get("type"));
            String value = (String) config.get("value");
            value = Objects.nonNull(value) ? value.trim() : "";

            Condition condition = null;
            if (type.equals(Type.TRIE)) {
                condition = new TrieCondition(value);
            } else if (type.equals(Type.REGEX)) {
                condition = new RegexCondition(value);
            } else {
                throw new Exception(String.format("Unknown condition type: %s.", type));
            }

            return Stream.of(condition);
        };
    }
}
