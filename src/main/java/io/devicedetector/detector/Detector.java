package io.devicedetector.detector;

import io.devicedetector.device.Device;
import io.devicedetector.exception.Exception;

public interface Detector<I, O extends Device> {
    O detect(I input) throws Exception;
}
