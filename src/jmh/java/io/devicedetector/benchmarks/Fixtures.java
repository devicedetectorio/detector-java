package io.devicedetector.benchmarks;

import io.devicedetector.useragent.RegexTokenizer;
import io.devicedetector.useragent.Tokenizer;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Fixtures {
    public final Set<String> patterns = new HashSet<>();
    public final Tokenizer tokenizer = new RegexTokenizer();
    public final Set<String> useragents = new HashSet<>();

    public Fixtures() throws URISyntaxException, IOException {
        InputStream inputStream = Fixtures.class.getClassLoader().getResourceAsStream("data/useragents.csv");
        String text = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());

        Set<String> tokens = Stream.of(text.split("\n"))
                .map(String::toLowerCase)
                .flatMap(userAgent -> tokenizer.tokenize(userAgent).stream())
                .map(token -> token.value)
                .collect(Collectors.toSet());

        patterns.addAll(tokens);
        useragents.addAll(Stream.of(text.split("\n")).map(String::toLowerCase).collect(Collectors.toSet()));
    }
}
