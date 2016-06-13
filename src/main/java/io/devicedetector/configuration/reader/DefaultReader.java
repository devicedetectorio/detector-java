package io.devicedetector.configuration.reader;

import io.devicedetector.condition.Condition;
import io.devicedetector.configuration.reader.stream.ConditionsFlatMapFactory;
import io.devicedetector.configuration.reader.stream.FeaturesFlatMapFactory;
import io.devicedetector.exception.Exception;
import io.devicedetector.feature.Feature;
import io.devicedetector.rule.Rule;
import org.slieb.throwables.FunctionWithThrowable;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DefaultReader implements Reader<Collection<Map<String, Object>>> {
    @Override
    @SuppressWarnings("unchecked")
    public Collection<Rule> read(Collection<Map<String, Object>> input) throws Exception {
        return new TreeSet<>(input
                .stream()
                .flatMap(readRules())
                .collect(Collectors.toList())
        );
    }

    @SuppressWarnings("unchecked")
    private FunctionWithThrowable<Map<String, Object>, Stream<Rule>, Exception> readRules() {
        return (config) -> {
            final Rule rule = new Rule(
                    (String) config.get("category"),
                    readFeatures(config),
                    (Integer) config.get("priority")
            );

            final List<Map<String, Object>> list = (List<Map<String, Object>>) config.get("conditions");
            final List<Condition> conditions = list
                    .stream()
                    .flatMap(ConditionsFlatMapFactory.create())
                    .collect(Collectors.toList());

            rule.conditions.addAll(conditions);

            return Stream.of(rule);
        };
    }

    @SuppressWarnings("unchecked")
    private Set<Feature> readFeatures(Map<String, Object> config) throws Exception {
        Map<String, Object> features = (Map<String, Object>) config.getOrDefault(
                "features", new HashMap<>()
        );

        return features.entrySet()
                .stream()
                .flatMap(FeaturesFlatMapFactory.create())
                .collect(Collectors.toSet());
    }
}
