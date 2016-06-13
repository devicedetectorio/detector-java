package io.devicedetector.factory;

import io.devicedetector.detector.Detector;
import io.devicedetector.device.Device;
import io.devicedetector.exception.Exception;

public interface DeviceDetectorFactory<I, O extends Device> {
    Detector<I, O> create() throws Exception;
}
