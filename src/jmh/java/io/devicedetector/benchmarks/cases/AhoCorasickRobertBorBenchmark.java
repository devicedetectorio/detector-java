package io.devicedetector.benchmarks.cases;

import io.devicedetector.benchmarks.Fixtures;
import org.ahocorasick.trie.Trie;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class AhoCorasickRobertBorBenchmark {

    @State(Scope.Benchmark)
    public static class BenchmarkState {
        @Setup(Level.Trial)
        public void doSetup() throws IOException, URISyntaxException {
            fixtures = new Fixtures();
            trie = Trie.builder()
                    .addKeywords(fixtures.patterns)
                    .build();
            System.out.println(String.format(
                    "Prepared %s useragents and %s patterns for benchmark purpose.",
                    fixtures.useragents.size(),
                    fixtures.patterns.size()
            ));
        }

        public Fixtures fixtures;
        public Trie trie;
    }

    @Benchmark
    @Group("stringOccurrence")
    @BenchmarkMode(Mode.SingleShotTime)
    public void measure(BenchmarkState state) {
        state.fixtures.useragents.stream().forEach(userAgent -> {
            state.trie.parseText(userAgent);
        });
    }
}
