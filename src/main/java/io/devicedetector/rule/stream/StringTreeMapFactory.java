package io.devicedetector.rule.stream;

import io.devicedetector.condition.TrieCondition;
import io.devicedetector.rule.Rule;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringTreeMapFactory {
    public static Map<String, TrieCondition> create(Collection<Rule> rules) {
        return rules.stream()
                .flatMap(rule -> rule.conditions.stream())
                .filter(condition -> condition instanceof TrieCondition)
                .flatMap(condition -> Stream.of(
                        new AbstractMap.SimpleEntry<>(((TrieCondition) condition).getValue(), (TrieCondition) condition))
                )
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
