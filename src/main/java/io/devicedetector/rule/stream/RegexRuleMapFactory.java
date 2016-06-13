package io.devicedetector.rule.stream;

import io.devicedetector.condition.RegexCondition;
import io.devicedetector.rule.Rule;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RegexRuleMapFactory {
    public static Map<RegexCondition, Rule> create(Collection<Rule> rules) {
        return rules.stream()
                .flatMap(rule -> rule.conditions.stream()
                        .filter(condition -> condition instanceof RegexCondition)
                        .flatMap(condition -> Stream.of(new AbstractMap.SimpleEntry<>((RegexCondition) condition, rule)))
                )
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
