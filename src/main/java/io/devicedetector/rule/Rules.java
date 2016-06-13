package io.devicedetector.rule;

import io.devicedetector.condition.Condition;
import io.devicedetector.condition.TrieCondition;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class Rules {
    public final Collection<Rule> collection;
    public final HashMap<String, Set<Occurence>> oc = new HashMap();

    public Rules(Collection<Rule> rules) {
        this.collection = rules;
        collection.stream()
                .flatMap(rule -> rule.conditions.stream().flatMap(condition -> Stream.of(new Occurence(rule, condition))))
                .filter(occurence -> occurence.condition instanceof TrieCondition)
                .forEach(occurence -> {
                    Set set = oc.getOrDefault((String) occurence.condition.getValue(), new HashSet<>());
                    set.add(occurence);
                    oc.put((String) occurence.condition.getValue(), set);
                });
    }

    public class Occurence {
        public final Rule rule;
        public final Condition condition;

        public Occurence(Rule rule, Condition condition) {
            this.rule = rule;
            this.condition = condition;
        }
    }
}
