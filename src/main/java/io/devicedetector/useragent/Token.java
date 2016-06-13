package io.devicedetector.useragent;

public class Token {
    public final TokenType type;
    public final String value;
    public final int order;
    public final int charAt;

    public Token(TokenType type, String value, int order, int charAt) {
        this.type = type;
        this.value = value;
        this.order = order;
        this.charAt = charAt;
    }
}
