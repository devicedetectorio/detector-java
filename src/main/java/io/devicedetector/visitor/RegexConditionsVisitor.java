package io.devicedetector.visitor;

import io.devicedetector.condition.RegexCondition;
import io.devicedetector.exception.Exception;
import io.devicedetector.rule.Rule;
import io.devicedetector.rule.matching.State;
import io.devicedetector.token.Token;
import io.devicedetector.token.UserAgentToken;

import java.util.Map;

public class RegexConditionsVisitor extends PriorityVisitor {
    private final State state;
    private final Map<RegexCondition, Rule> regexConditionRuleMap;

    public RegexConditionsVisitor(State state, Map<RegexCondition, Rule> regexConditionRuleMap) {
        this.state = state;
        this.regexConditionRuleMap = regexConditionRuleMap;
    }

    @Override
    public boolean accept(Token token, Map<String, Object> collator) {
        return token instanceof UserAgentToken;
    }

    @Override
    public Result visit(Token token, Map<String, Object> collator) throws Exception {
        final String userAgent = (String) token.getData();

        regexConditionRuleMap
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().getValue().matcher(userAgent).find())
                .forEach(entry -> state.markFulfilled(entry.getValue(), entry.getKey()));

        return Result.CONTINUE;
    }
}
