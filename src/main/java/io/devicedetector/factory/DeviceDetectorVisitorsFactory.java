package io.devicedetector.factory;

import com.hankcs.algorithm.AhoCorasickDoubleArrayTrie;
import io.devicedetector.condition.TrieCondition;
import io.devicedetector.configuration.loader.Loader;
import io.devicedetector.configuration.loader.YamlLoader;
import io.devicedetector.configuration.reader.DefaultReader;
import io.devicedetector.detector.Detector;
import io.devicedetector.detector.TokenPoolDetector;
import io.devicedetector.device.Device;
import io.devicedetector.exception.Exception;
import io.devicedetector.rule.Rule;
import io.devicedetector.rule.matching.DefaultState;
import io.devicedetector.rule.stream.DefaultFeaturesExtractor;
import io.devicedetector.rule.stream.RegexRuleMapFactory;
import io.devicedetector.rule.stream.StringTreeMapFactory;
import io.devicedetector.rule.stream.TrieRuleMapFactory;
import io.devicedetector.token.Token;
import io.devicedetector.visitor.*;
import io.devicedetector.visitor.trie.AhoCorasickDoubleArrayTrieVisitor;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class DeviceDetectorVisitorsFactory implements DeviceDetectorFactory<Set<Token>, Device> {
    private Collection<Rule> rules;
    private io.devicedetector.rule.matching.State state;

    public DeviceDetectorVisitorsFactory() throws Exception {
        rules = new DefaultReader().read(prepareLoader().load());
        state = new DefaultState();
    }

    @Override
    public Detector<Set<Token>, Device> create() throws Exception {
        final AhoCorasickDoubleArrayTrie<TrieCondition> trie = new AhoCorasickDoubleArrayTrie<>();
        trie.build(StringTreeMapFactory.create(rules));

        final Set<Visitor> visitors = new TreeSet<>();
        RulesVisitor rulesVisitor = new RulesVisitor(state, new DefaultFeaturesExtractor());
        rulesVisitor.setPriority(-1000);

        visitors.add(new AhoCorasickDoubleArrayTrieVisitor(state, TrieRuleMapFactory.create(this.rules), trie));
        visitors.add(new RegexConditionsVisitor(state, RegexRuleMapFactory.create(rules)));
        visitors.add(rulesVisitor);
        visitors.add(new BlinkBrowserEngineVisitor(-1000));
        visitors.add(new EndPointVisitor(-1000));

        return new TokenPoolDetector(new SetManager(visitors));
    }

    private Loader<Collection<Map<String, Object>>> prepareLoader() throws Exception {
        Path path;
        try {
            path = new File(this.getClass().getResource("/rules/useragent/yml").toURI()).toPath();
        } catch (URISyntaxException e) {
            throw new Exception("Error during building rules File object.", e);
        }

        return new YamlLoader(new Yaml(), path);
    }
}
