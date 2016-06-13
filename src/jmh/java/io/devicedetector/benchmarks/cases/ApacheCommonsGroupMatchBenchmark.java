package io.devicedetector.benchmarks.cases;

import io.devicedetector.benchmarks.Fixtures;
import org.apache.commons.lang3.StringUtils;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class ApacheCommonsGroupMatchBenchmark {

    @State(Scope.Benchmark)
    public static class BenchmarkState {
        @Setup(Level.Trial)
        public void doSetup() throws IOException, URISyntaxException {
            fixtures = new Fixtures();

            System.out.println(String.format(
                    "Prepared %s useragents and %s patterns for benchmark purpose.",
                    fixtures.useragents.size(),
                    fixtures.patterns.size()
            ));
        }

        public Fixtures fixtures;
    }

    @Benchmark
    @Group("groupMatching")
    @BenchmarkMode(Mode.SingleShotTime)
    public void measure(BenchmarkState state) {
        state.fixtures.useragents.stream().forEach(userAgent -> {
            StringUtils.substringBetween(userAgent, "chrome/", " ");
            StringUtils.substringBetween(userAgent, "safari/", " ");
            StringUtils.substringBetween(userAgent, "firefox/", " ");
        });
    }
}
