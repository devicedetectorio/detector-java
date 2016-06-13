package io.devicedetector.tests.unit.factory;

import io.devicedetector.detector.Detector;
import io.devicedetector.device.Device;
import io.devicedetector.exception.Exception;
import io.devicedetector.factory.DeviceDetectorVisitorsFactory;
import io.devicedetector.token.Token;
import io.devicedetector.token.UserAgentToken;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DeviceDetectorVisitorsFactoryTest {
    @Test
    public void create() throws Exception {
        String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 1091) AppleWebKit/537.36 (KHTML like Gecko) Chrome/33.0.1750.91 Safari/537.36 OPR/20.0.1387.37 (Edition Next)";
        userAgent = userAgent.toLowerCase();

        DeviceDetectorVisitorsFactory deviceDetectorFactory = new DeviceDetectorVisitorsFactory();
        Detector<Set<Token>, Device> detector = deviceDetectorFactory.create();

        Set<Token> tokens = new HashSet<>();
        tokens.add(new UserAgentToken(userAgent));

        Device device = detector.detect(tokens);

        assertNotNull(device);
        assertTrue(device.isRecognized());
    }
}