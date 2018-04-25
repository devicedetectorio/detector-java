package io.devicedetector.tests.unit.condition;

import io.devicedetector.condition.Condition;
import io.devicedetector.condition.RegexCondition;
import org.junit.Test;

import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class RegexConditionTest {
    @Test
    public void itShouldCreateRegexCondition()
    {
        Condition<Pattern> condition = new RegexCondition("chrome/");
        Pattern pattern = condition.getValue();

        assertEquals("chrome/", pattern.pattern());
    }

    @Test
    public void itShouldImplementsEqualityBaseOnPattern()
    {
        Condition<Pattern> condition1 = new RegexCondition("chrome/");
        Condition<Pattern> condition2 = new RegexCondition("chrome/");
        Condition<Pattern> condition3 = new RegexCondition("firefox/");

        assertTrue(condition1.equals(condition2));
        assertFalse(condition3.equals(condition1));
        assertFalse(condition3.equals(condition2));
    }
}
