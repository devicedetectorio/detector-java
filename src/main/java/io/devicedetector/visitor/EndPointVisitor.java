package io.devicedetector.visitor;

import io.devicedetector.exception.Exception;
import io.devicedetector.token.Token;

import java.util.Map;

public class EndPointVisitor extends PriorityVisitor {
    public EndPointVisitor(int priority) {
        this.setPriority(priority);
    }

    @Override
    public boolean accept(Token token, Map<String, Object> collator) {
        return true;
    }

    @Override
    public Result visit(Token token, Map<String, Object> collator) throws Exception {
        collator.putIfAbsent("is_console", false);
        collator.putIfAbsent("is_bot", false);
        collator.putIfAbsent("is_smarttv", false);
        collator.putIfAbsent("is_mobile", false);
        collator.putIfAbsent("is_desktop", (!((Boolean) collator.get("is_mobile"))));

        return Result.CONTINUE;
    }
}
