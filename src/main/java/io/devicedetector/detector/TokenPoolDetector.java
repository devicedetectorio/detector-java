package io.devicedetector.detector;

import io.devicedetector.device.Device;
import io.devicedetector.device.MapDevice;
import io.devicedetector.exception.Exception;
import io.devicedetector.token.Token;
import io.devicedetector.visitor.Manager;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TokenPoolDetector implements Detector<Set<Token>, Device> {
    private final Manager manager;

    public TokenPoolDetector(Manager manager) {
        this.manager = manager;
    }

    @Override
    public Device detect(Set<Token> input) throws Exception {
        Map<String, Object> collator = new HashMap<>();
        manager.visit(input, collator);

        return new MapDevice(collator);
    }
}
