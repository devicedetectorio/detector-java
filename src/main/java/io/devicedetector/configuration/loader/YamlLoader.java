package io.devicedetector.configuration.loader;

import io.devicedetector.configuration.loader.exception.LoaderException;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import static org.slieb.throwables.FunctionWithThrowable.castFunctionWithThrowable;

public class YamlLoader implements Loader<Collection<Map<String, Object>>> {
    private final Yaml yaml;
    private final Path path;

    public YamlLoader(Yaml yaml, Path path) {
        this.yaml = yaml;
        this.path = path;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Map<String, Object>> load() throws LoaderException {
        final StringBuilder stringBuilder = new StringBuilder();
        try {
            Files.walk(path)
                    .filter(YamlLoader::isYaml)
                    .map(castFunctionWithThrowable(YamlLoader::read))
                    .forEach(stringBuilder::append);
        } catch (Exception e) {
            throw new LoaderException("Error during loading YAML configuration.", e);
        }

        return (ArrayList<Map<String, Object>>) yaml.load(stringBuilder.toString());
    }

    private static boolean isYaml(Path path) {
        return path.toFile().isFile() && path.toString().endsWith(".yml");
    }

    private static String read(Path path) throws IOException {
        return new String(Files.readAllBytes(path)).concat("\n");
    }
}
