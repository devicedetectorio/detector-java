package io.devicedetector.visitor;

import io.devicedetector.exception.Exception;
import io.devicedetector.token.Token;

import java.util.Map;

public interface Visitor {
    boolean accept(Token token, Map<String, Object> collator);

    Result visit(Token token, Map<String, Object> collator) throws Exception;
}
