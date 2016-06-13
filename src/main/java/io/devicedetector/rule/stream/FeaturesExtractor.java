package io.devicedetector.rule.stream;

import io.devicedetector.rule.Rule;
import io.devicedetector.token.UserAgentToken;

import java.util.Map;
import java.util.Set;

public interface FeaturesExtractor {
    Map<String, Object> extract(UserAgentToken userAgentToken, Set<Rule> rules);
}
