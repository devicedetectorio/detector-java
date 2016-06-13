package io.devicedetector.benchmarks.cases;

import com.hankcs.algorithm.AhoCorasickDoubleArrayTrie;
import io.devicedetector.benchmarks.Fixtures;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class AhoCorasickHankcsBenchmark {

    @State(Scope.Benchmark)
    public static class BenchmarkState {
        @Setup(Level.Trial)
        public void doSetup() throws IOException, URISyntaxException {
            map = new TreeMap<String, String>();
            fixtures = new Fixtures();
            fixtures.patterns.stream().forEach(pattern -> {
                map.put(pattern, pattern);
            });

            trie = new AhoCorasickDoubleArrayTrie<String>();
            trie.build(map);

            System.out.println(String.format(
                    "Prepared %s useragents and %s patterns for benchmark purpose.",
                    fixtures.useragents.size(),
                    fixtures.patterns.size()
            ));
        }

        public Fixtures fixtures;
        public TreeMap<String, String> map;
        public AhoCorasickDoubleArrayTrie<String> trie;
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
