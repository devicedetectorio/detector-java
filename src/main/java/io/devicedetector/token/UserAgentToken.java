package io.devicedetector.token;

public class UserAgentToken implements Token<String> {
    private final String userAgent;

    public UserAgentToken(String userAgent) {
        this.userAgent = userAgent;
    }

    @Override
    public String getData() {
        return this.userAgent;
    }
}
