package seedu.address;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TestUtil.basicEqualsTests;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import javafx.application.Application;

public class AppParametersTest {

    private final ParametersStub parametersStub = new ParametersStub();
    private final AppParameters expected = new AppParameters();

    @Test
    public void getConfigPath_returnsSetConfigPath() {
        expected.setConfigPath(Paths.get("config.json"));
        assertEquals(expected.getConfigPath(), Paths.get("config.json"));
    }

    @Test
    public void parse_validConfigPath_success() {
        parametersStub.namedParameters.put("config", "config.json");
        expected.setConfigPath(Paths.get("config.json"));
        assertEquals(expected, AppParameters.parse(parametersStub));
    }

    @Test
    public void parse_nullConfigPath_success() {
        parametersStub.namedParameters.put("config", null);
        assertEquals(expected, AppParameters.parse(parametersStub));
    }

    @Test
    public void parse_invalidConfigPath_success() {
        parametersStub.namedParameters.put("config", "a\0");
        expected.setConfigPath(null);
        assertEquals(expected, AppParameters.parse(parametersStub));
    }

    @Test
    public void equals_basicTests() {
        // basic equals tests
        basicEqualsTests(expected);
    }

    @Test
    public void equals_sameConfigPath_returnsTrue() {
        expected.setConfigPath(Paths.get("config.json"));
        AppParameters other = new AppParameters();
        other.setConfigPath(Paths.get("config.json"));
        assertTrue(expected.equals(other));
    }

    @Test
    public void equals_differentConfigPath_returnsFalse() {
        expected.setConfigPath(Paths.get("config.json"));
        AppParameters other = new AppParameters();
        other.setConfigPath(Paths.get("config2.json"));
        assertFalse(expected.equals(other));
    }

    private static class ParametersStub extends Application.Parameters {
        private final Map<String, String> namedParameters = new HashMap<>();

        @Override
        public List<String> getRaw() {
            throw new AssertionError("should not be called");
        }

        @Override
        public List<String> getUnnamed() {
            throw new AssertionError("should not be called");
        }

        @Override
        public Map<String, String> getNamed() {
            return Collections.unmodifiableMap(namedParameters);
        }
    }

}
