package io.devicedetector.useragent;

import java.util.List;

public interface Tokenizer {
    List<Token> tokenize(String userAgent);
}
