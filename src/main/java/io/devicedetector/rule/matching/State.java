package io.devicedetector.rule.matching;

import io.devicedetector.condition.Condition;
import io.devicedetector.rule.Rule;

import java.util.Collection;

public interface State {
    void markFulfilled(Rule rule, Condition condition);

    Collection<Rule> fulfilled();

    void clean();
}
