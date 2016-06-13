package io.devicedetector.configuration.reader;

import io.devicedetector.exception.Exception;
import io.devicedetector.rule.Rule;

import java.util.Collection;
import java.util.Map;

public interface Reader<I extends Collection<Map<String, Object>>> {
    Collection<Rule> read(I input) throws Exception;
}
