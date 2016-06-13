package io.devicedetector.rule;

import io.devicedetector.condition.Condition;
import io.devicedetector.feature.Feature;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Rule implements Comparable<Rule>, Cloneable {
    public final List<Condition> conditions = new ArrayList<>();
    public final String category;
    public final Collection<Feature> features;
    public final int priority;

    public Rule(String category, Collection<Feature> features, int priority) {
        this.category = category;
        this.features = features;
        this.priority = priority;
    }

    @Override
    public int compareTo(Rule rule) {
        if (rule.priority - this.priority == 0) {
            return 1;
        }

        return rule.priority > this.priority ? -1 : 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rule)) return false;

        Rule rule = (Rule) o;

        if (!conditions.equals(rule.conditions)) return false;
        if (!category.equals(rule.category)) return false;
        return features.equals(rule.features);
    }

    @Override
    public int hashCode() {
        int result = conditions.hashCode();
        result = 31 * result + category.hashCode();
        result = 31 * result + features.hashCode();
        return result;
    }
}
