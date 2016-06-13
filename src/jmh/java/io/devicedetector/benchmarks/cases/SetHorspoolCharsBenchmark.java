package io.devicedetector.benchmarks.cases;

import io.devicedetector.benchmarks.Fixtures;
import net.amygdalum.stringsearchalgorithms.search.MatchOption;
import net.amygdalum.stringsearchalgorithms.search.StringFinder;
import net.amygdalum.stringsearchalgorithms.search.chars.SetHorspool;
import net.amygdalum.util.io.StringCharProvider;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class SetHorspoolCharsBenchmark {

    @State(Scope.Benchmark)
    public static class BenchmarkState {
        @Setup(Level.Trial)
        public void doSetup() throws IOException, URISyntaxException {
            fixtures = new Fixtures();
            stringSearch = new SetHorspool(fixtures.patterns);
            System.out.println(String.format(
                    "Prepared %s useragents and %s patterns for benchmark purpose.",
                    fixtures.useragents.size(),
                    fixtures.patterns.size()
            ));
        }

        public Fixtures fixtures;
        public SetHorspool stringSearch;
    }

    @Benchmark
    @Group("stringOccurrence")
    @BenchmarkMode(Mode.SingleShotTime)
    public void measure(BenchmarkState state) {
        state.fixtures.useragents.stream().forEach(userAgent -> {
            final StringFinder finder = state.stringSearch.createFinder(
                    new StringCharProvider(userAgent, 0), MatchOption.LONGEST_MATCH
            );
            finder.findAll();
        });
    }
}
