package io.devicedetector.benchmarks.algo;

import io.devicedetector.benchmarks.Fixtures;
import net.amygdalum.stringsearchalgorithms.search.MatchOption;
import net.amygdalum.stringsearchalgorithms.search.StringFinder;
import net.amygdalum.stringsearchalgorithms.search.bytes.AhoCorasick;
import net.amygdalum.util.io.StringByteProvider;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class AhoCorasickBytesBenchmark {

    @State(Scope.Benchmark)
    public static class BenchmarkState {
        @Setup(Level.Trial)
        public void doSetup() throws IOException, URISyntaxException {
            fixtures = new Fixtures();
            stringSearch = new AhoCorasick(fixtures.patterns, Charset.defaultCharset());
            System.out.println(String.format(
                    "Prepared %s useragents and %s patterns for benchmark purpose.",
                    fixtures.useragents.size(),
                    fixtures.patterns.size()
            ));
        }

        public Fixtures fixtures;
        public AhoCorasick stringSearch;
    }

    @Benchmark
    @Group("stringOccurrence")
    @BenchmarkMode(Mode.SingleShotTime)
    public void measure(BenchmarkState state) {
        state.fixtures.useragents.stream().forEach(userAgent -> {
            final StringFinder finder = state.stringSearch.createFinder(
                    new StringByteProvider(userAgent, 0), MatchOption.LONGEST_MATCH
            );
            finder.findAll();
        });
    }
}
