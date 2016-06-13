package io.devicedetector.useragent;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTokenizer implements Tokenizer {
    @Override
    public List<Token> tokenize(String userAgent) {
        List<Token> tokens = new ArrayList<>();
        Pattern tokenPatterns = Pattern.compile(preparePattern());
        Matcher matcher = tokenPatterns.matcher(userAgent.toLowerCase());

        while (matcher.find()) {
            if (matcher.group(TokenType.TEXT.name()) != null) {
                tokens.add(new Token(TokenType.TEXT, matcher.group(TokenType.TEXT.name()), tokens.size(), matcher.start()));
            } else if (matcher.group(TokenType.URL.name()) != null) {
                tokens.add(new Token(TokenType.URL, matcher.group(TokenType.URL.name()).trim(), tokens.size(), matcher.start()));
            }
        }

        return tokens;
    }

    protected String preparePattern() {
        StringBuffer tokenBuffer = new StringBuffer();
        for (TokenType tokenType : TokenType.values()) {
            tokenBuffer.append(String.format("|(?<%s>%s)", tokenType.name(), tokenType.pattern));
        }

        return tokenBuffer.substring(1);
    }
}
