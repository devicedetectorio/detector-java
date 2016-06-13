package io.devicedetector.visitor;

import io.devicedetector.exception.Exception;
import io.devicedetector.token.Token;

import java.util.Map;
import java.util.Set;

public class SetManager implements Manager {
    public final Set<Visitor> visitors;

    public SetManager(Set<Visitor> visitors) {
        this.visitors = visitors;
    }

    @Override
    public void visit(Set<Token> tokenPool, Map<String, Object> collator) throws Exception {
        for (Visitor visitor : visitors) {
            for (Token token : tokenPool) {
                if (visitor.accept(token, collator)) {
                    if (Result.BREAK.equals(visitor.visit(token, collator))) {
                        break;
                    }
                }
            }
        }
    }
}
