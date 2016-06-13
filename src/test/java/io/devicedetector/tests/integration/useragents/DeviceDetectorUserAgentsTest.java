package io.devicedetector.tests.integration.useragents;

import io.devicedetector.configuration.loader.YamlLoader;
import io.devicedetector.detector.Detector;
import io.devicedetector.device.Device;
import io.devicedetector.exception.Exception;
import io.devicedetector.factory.DeviceDetectorVisitorsFactory;
import io.devicedetector.token.Token;
import io.devicedetector.token.UserAgentToken;
import org.junit.Before;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class DeviceDetectorUserAgentsTest {
    private List<IntegrationTestData> integrationTestDataList;
    private Detector<Set<Token>, Device> detector;

    @Before
    public void setUpLoader() throws Exception {
        Path path;
        try {
            path = new File(DeviceDetectorUserAgentsTest.class.getResource("/useragents").toURI()).toPath();
        } catch (URISyntaxException e) {
            throw new Exception("Error during building test data path.", e);
        }

        final YamlLoader loader = new YamlLoader(new Yaml(), path);

        integrationTestDataList = loader.load()
                .stream()
                .map(map -> new IntegrationTestData(
                        (String) map.get("useragent"),
                        (Map<String, Object>) map.get("features")
                ))
                .collect(Collectors.toList());

        final DeviceDetectorVisitorsFactory deviceDetectorFactory = new DeviceDetectorVisitorsFactory();
        detector = deviceDetectorFactory.create();
    }

    @Test
    public void testFeatures() {
        integrationTestDataList.forEach(integrationTestData -> {
            final Set<Token> tokens = new HashSet<>();
            tokens.add(new UserAgentToken(integrationTestData.userAgent.toLowerCase()));

            try {
                final Device device = detector.detect(tokens);
                assertEquals(
                        String.format("UserAgent '%s' features does not match.", integrationTestData.userAgent),
                        new TreeMap<>(integrationTestData.features).toString(),
                        new TreeMap<>(device.getFeatures()).toString()
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public class IntegrationTestData {
        public final String userAgent;
        public final Map<String, Object> features;

        public IntegrationTestData(String userAgent, Map<String, Object> features) {
            this.userAgent = userAgent;
            this.features = features;
        }
    }
}
