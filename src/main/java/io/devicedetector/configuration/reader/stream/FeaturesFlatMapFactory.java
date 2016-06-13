package io.devicedetector.configuration.reader.stream;

import io.devicedetector.exception.Exception;
import io.devicedetector.feature.Feature;
import io.devicedetector.feature.KeyValueFeature;
import io.devicedetector.feature.StringBetween;
import io.devicedetector.feature.Type;
import org.slieb.throwables.FunctionWithThrowable;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class FeaturesFlatMapFactory {
    @SuppressWarnings("unchecked")
    public static FunctionWithThrowable<Map.Entry<String, Object>, Stream<Feature>, Exception> create() {
        return (entry) -> {
            final String name = entry.getKey();
            final Object value = entry.getValue();
            Optional<Feature> feature = Optional.empty();

            if (value instanceof String | value instanceof Boolean) {
                feature = Optional.of(new KeyValueFeature(name, value));
            } else if (value instanceof Map) {
                final Map<String, Object> valueAsMap = (Map<String, Object>) value;
                final Type type = Type.create((String) valueAsMap.get("type"));

                switch (type) {
                    case substringBetween:
                        feature = createStringBetweenFeature(name, valueAsMap);
                        break;
                }
            }

            return feature.map(Stream::of).orElseGet(Stream::empty);
        };
    }

    private static Optional<Feature> createStringBetweenFeature(String name, Map<String, Object> configuration) {
        final String open = ((String) configuration.getOrDefault("open", " "));
        final String close = ((String) configuration.getOrDefault("close", " "));

        return Optional.of(new StringBetween(name, open, close));
    }
}
