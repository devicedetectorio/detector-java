package io.devicedetector.device;

import java.util.Map;

public class MapDevice implements Device {
    private final Map<String, Object> features;

    public MapDevice(Map<String, Object> features) {
        this.features = features;
    }

    @Override
    public boolean isRecognized() {
        return features.size() > 0;
    }

    @Override
    public Object getFeature(String key) {
        return features.getOrDefault(key, null);
    }

    @Override
    public boolean hasFeature(String key) {
        return features.containsKey(key);
    }

    @Override
    public Map<String, Object> getFeatures() {
        return features;
    }
}
