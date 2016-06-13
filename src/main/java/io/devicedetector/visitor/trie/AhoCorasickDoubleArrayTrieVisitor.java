package io.devicedetector.visitor.trie;

import com.hankcs.algorithm.AhoCorasickDoubleArrayTrie;
import io.devicedetector.condition.TrieCondition;
import io.devicedetector.exception.Exception;
import io.devicedetector.rule.Rule;
import io.devicedetector.rule.matching.State;
import io.devicedetector.token.Token;
import io.devicedetector.token.UserAgentToken;
import io.devicedetector.visitor.PriorityVisitor;
import io.devicedetector.visitor.Result;

import java.util.Map;

public class AhoCorasickDoubleArrayTrieVisitor extends PriorityVisitor {
    private final State state;
    private final Map<TrieCondition, Rule> trieRuleMap;
    private final AhoCorasickDoubleArrayTrie<TrieCondition> trie;

    public AhoCorasickDoubleArrayTrieVisitor(State state, Map<TrieCondition, Rule> trieRuleMap, AhoCorasickDoubleArrayTrie<TrieCondition> trie) {
        this.state = state;
        this.trieRuleMap = trieRuleMap;
        this.trie = trie;
    }

    @Override
    public boolean accept(Token token, Map<String, Object> collator) {
        return token instanceof UserAgentToken;
    }

    @Override
    public Result visit(Token token, Map<String, Object> collator) throws Exception {
        trie.parseText((String) token.getData(), (begin, end, trie) -> {
            state.markFulfilled(trieRuleMap.get(trie), trie);
        });

        return Result.CONTINUE;
    }
}
