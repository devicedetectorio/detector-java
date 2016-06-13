package io.devicedetector.rule.matching;

import io.devicedetector.condition.Condition;
import io.devicedetector.rule.Rule;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class DefaultState implements State {
    private Map<Rule, Integer> counter = new HashMap<>();
    private Map<Condition, Rule> matrix = new HashMap<>();

    @Override
    public void markFulfilled(Rule rule, Condition condition) {
        if (!matrix.containsKey(condition)) {
            matrix.putIfAbsent(condition, rule);
            counter.putIfAbsent(rule, rule.conditions.size());
            counter.put(rule, counter.get(rule) - 1);
        }
    }

    @Override
    public Collection<Rule> fulfilled() {
        return counter
                .entrySet()
                .stream()
                .filter(ruleIntegerEntry -> ruleIntegerEntry.getValue() == 0)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    @Override
    public void clean() {
        counter.clear();
        matrix.clear();
    }
}
