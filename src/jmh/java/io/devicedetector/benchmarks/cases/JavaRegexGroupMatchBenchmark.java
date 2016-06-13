package io.devicedetector.benchmarks.cases;

import io.devicedetector.benchmarks.Fixtures;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class JavaRegexGroupMatchBenchmark {

    @State(Scope.Benchmark)
    public static class BenchmarkState {
        @Setup(Level.Trial)
        public void doSetup() throws IOException, URISyntaxException {
            fixtures = new Fixtures();

            patterns = new ArrayList<>();
            patterns.add(Pattern.compile("chrome/(?<version>[^\\s]+)"));
            patterns.add(Pattern.compile("safari/(?<version>[^\\s]+)"));
            patterns.add(Pattern.compile("firefox/(?<version>[^\\s]+)"));

            System.out.println(String.format(
                    "Prepared %s useragents and %s patterns for benchmark purpose.",
                    fixtures.useragents.size(),
                    fixtures.patterns.size()
            ));
        }

        public Fixtures fixtures;
        public List<Pattern> patterns;
    }

    @Benchmark
    @Group("groupMatching")
    @BenchmarkMode(Mode.SingleShotTime)
    public void measure(BenchmarkState state) {
        state.fixtures.useragents.stream().forEach(userAgent -> {
            state.patterns.stream().forEach(pattern -> {
                Matcher matcher = pattern.matcher(userAgent);
                while (matcher.find()) {
                    matcher.group("version");
                }
            });
        });
    }
}
