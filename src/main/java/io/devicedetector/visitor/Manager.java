package io.devicedetector.visitor;

import io.devicedetector.exception.Exception;
import io.devicedetector.token.Token;

import java.util.Map;
import java.util.Set;

public interface Manager {
    void visit(Set<Token> tokenPool, Map<String, Object> collator) throws Exception;
}
