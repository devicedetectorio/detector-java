package io.devicedetector.device;

import java.util.Map;

public interface Device {
    boolean isRecognized();

    Object getFeature(String key);

    boolean hasFeature(String key);

    Map<String, Object> getFeatures();
}
