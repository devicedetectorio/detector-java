package io.devicedetector.configuration.loader;

import io.devicedetector.configuration.loader.exception.LoaderException;

import java.util.Collection;
import java.util.Map;

public interface Loader<T extends Collection<Map<String, Object>>> {
    T load() throws LoaderException;
}
