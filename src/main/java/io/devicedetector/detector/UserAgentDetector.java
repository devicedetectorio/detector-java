package io.devicedetector.detector;

import io.devicedetector.device.MapDevice;
import io.devicedetector.exception.Exception;
import io.devicedetector.token.Token;
import io.devicedetector.token.UserAgentToken;

import java.util.HashSet;
import java.util.Set;

public class UserAgentDetector implements Detector<String, MapDevice> {
    private Detector<Set<Token>, MapDevice> tokenPoolDetector;

    public UserAgentDetector(Detector<Set<Token>, MapDevice> tokenPoolDetector) {
        this.tokenPoolDetector = tokenPoolDetector;
    }

    @Override
    public MapDevice detect(String userAgent) throws Exception {
        Set<Token> tokens = new HashSet<>();
        tokens.add(new UserAgentToken(userAgent));

        return this.tokenPoolDetector.detect(tokens);
    }
}
