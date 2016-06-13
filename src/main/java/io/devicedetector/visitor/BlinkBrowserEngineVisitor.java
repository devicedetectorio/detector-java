package io.devicedetector.visitor;

import io.devicedetector.exception.Exception;
import io.devicedetector.token.Token;

import java.util.HashMap;
import java.util.Map;

public class BlinkBrowserEngineVisitor extends PriorityVisitor {
    private final HashMap<String, Integer> browsers = new HashMap<String, Integer>() {{
        put("chrome", 28);
        put("chromium", 28);
        put("opera", 15);
    }};

    public BlinkBrowserEngineVisitor(int priority) {
        this.setPriority(priority);
    }

    @Override
    public boolean accept(Token token, Map<String, Object> collator) {
        return true;
    }

    @Override
    public Result visit(Token token, Map<String, Object> collator) throws Exception {
        if (!collator.containsKey("browser_engine")
                || !collator.containsKey("browser_family")
                || !collator.containsKey("browser_version")) {
            return Result.CONTINUE;
        }

        if ("webkit".equalsIgnoreCase((String) collator.get("browser_engine"))) {
            final String browserFamily = (String) collator.get("browser_family");
            final String[] browserVersionParts = ((String) collator.get("browser_version"))
                    .split("\\.");

            if ((browserVersionParts.length > 0) && (Integer.parseInt(browserVersionParts[0]) >= browsers.get(browserFamily.toLowerCase()))) {
                collator.put("browser_engine", "Blink");
            }
        }

        return Result.CONTINUE;
    }
}
