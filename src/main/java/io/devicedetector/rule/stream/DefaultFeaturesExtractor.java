package io.devicedetector.rule.stream;

import io.devicedetector.feature.KeyValueFeature;
import io.devicedetector.feature.StringBetween;
import io.devicedetector.rule.Rule;
import io.devicedetector.token.UserAgentToken;
import org.apache.commons.lang3.StringUtils;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DefaultFeaturesExtractor implements FeaturesExtractor {
    @Override
    public Map<String, Object> extract(UserAgentToken userAgentToken, Set<Rule> rules) {
        return rules
                .stream()
                .flatMap(rule -> rule.features.stream())
                .flatMap(feature -> {
                    Optional<Map.Entry<String, Object>> optional = Optional.empty();

                    if (feature instanceof KeyValueFeature) {
                        optional = Optional.of(new AbstractMap.SimpleEntry<>(
                                feature.getName(),
                                ((KeyValueFeature) feature).getValue())
                        );
                    } else if (feature instanceof StringBetween) {
                        String value = StringUtils.substringBetween(
                                userAgentToken.getData(),
                                ((StringBetween) feature).getOpen(),
                                ((StringBetween) feature).getClose()
                        );
                        optional = Optional.of(new AbstractMap.SimpleEntry<>(
                                feature.getName(),
                                value)
                        );
                    }

                    return optional.map(Stream::of).orElseGet(Stream::empty);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
