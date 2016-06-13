package io.devicedetector.visitor;

import io.devicedetector.exception.Exception;
import io.devicedetector.rule.Rule;
import io.devicedetector.rule.matching.State;
import io.devicedetector.rule.stream.FeaturesExtractor;
import io.devicedetector.token.Token;
import io.devicedetector.token.UserAgentToken;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class RulesVisitor extends PriorityVisitor {
    private final State state;
    private final FeaturesExtractor featuresExtractor;

    public RulesVisitor(State state, FeaturesExtractor featuresExtractor) {
        this.state = state;
        this.featuresExtractor = featuresExtractor;
    }

    @Override
    public boolean accept(Token token, Map<String, Object> collator) {
        return token instanceof UserAgentToken;
    }

    @Override
    public Result visit(Token token, Map<String, Object> collator) throws Exception {
        final UserAgentToken userAgentToken = (UserAgentToken) token;

        final Set<Rule> rules = new TreeSet<>(state.fulfilled());

        collator.putAll(featuresExtractor.extract(userAgentToken, rules));

        state.clean();

        return Result.CONTINUE;
    }
}
