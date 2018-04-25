package io.devicedetector.tests.unit.condition;

import io.devicedetector.condition.Condition;
import io.devicedetector.condition.TrieCondition;
import org.junit.Test;

import static org.junit.Assert.*;

public class TrieConditionTest {
    @Test
    public void itShouldCreateTrieCondition()
    {
        Condition<String> condition = new TrieCondition("chrome/");

        assertEquals("chrome/", condition.getValue());
    }

    @Test
    public void itShouldImplementsEqualityBaseOnTrie()
    {
        Condition<String> condition1 = new TrieCondition("chrome/");
        Condition<String> condition2 = new TrieCondition("chrome/");
        Condition<String> condition3 = new TrieCondition("firefox/");

        assertTrue(condition1.equals(condition2));
        assertFalse(condition3.equals(condition1));
        assertFalse(condition3.equals(condition2));
    }
}
