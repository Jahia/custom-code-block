package org.jahia.community.modules.customCodeBlock;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Ensures EN and FR resource bundles are consistent:
 * same keys, no empty values.
 */
public class ResourceBundleConsistencyTest {

    private Properties loadBundle(String path) throws IOException {
        Properties props = new Properties();
        try (InputStream is = getClass().getResourceAsStream(path)) {
            assertNotNull("Bundle not found: " + path, is);
            props.load(new InputStreamReader(is, StandardCharsets.UTF_8));
        }
        return props;
    }

    @Test
    public void bothBundlesHaveSameKeys() throws IOException {
        Properties en = loadBundle("/resources/CustomCodeBlock.properties");
        Properties fr = loadBundle("/resources/CustomCodeBlock_fr.properties");

        Set<Object> enKeys = en.keySet();
        Set<Object> frKeys = fr.keySet();

        for (Object key : enKeys) {
            assertTrue("Key missing in FR bundle: " + key, frKeys.contains(key));
        }
        for (Object key : frKeys) {
            assertTrue("Key missing in EN bundle: " + key, enKeys.contains(key));
        }
    }

    @Test
    public void noEmptyValuesInEnBundle() throws IOException {
        Properties en = loadBundle("/resources/CustomCodeBlock.properties");
        for (String key : en.stringPropertyNames()) {
            assertFalse("Empty value for key: " + key, en.getProperty(key).trim().isEmpty());
        }
    }

    @Test
    public void noEmptyValuesInFrBundle() throws IOException {
        Properties fr = loadBundle("/resources/CustomCodeBlock_fr.properties");
        for (String key : fr.stringPropertyNames()) {
            assertFalse("Empty value for key: " + key, fr.getProperty(key).trim().isEmpty());
        }
    }

    @Test
    public void noHtmlTagsInValues() throws IOException {
        Properties en = loadBundle("/resources/CustomCodeBlock.properties");
        Properties fr = loadBundle("/resources/CustomCodeBlock_fr.properties");
        for (Properties props : new Properties[]{en, fr}) {
            for (String key : props.stringPropertyNames()) {
                String value = props.getProperty(key);
                assertFalse("HTML tag found in value for key: " + key, value.contains("<") || value.contains(">"));
            }
        }
    }

    @Test
    public void tooltipKeysHaveCorrespondingLabelKey() throws IOException {
        Properties en = loadBundle("/resources/CustomCodeBlock.properties");
        for (String key : en.stringPropertyNames()) {
            if (key.endsWith(".ui.tooltip")) {
                String labelKey = key.replace(".ui.tooltip", "");
                assertNotNull("Tooltip key '" + key + "' has no corresponding label key: " + labelKey,
                        en.getProperty(labelKey));
            }
        }
    }

    @Test
    public void requiredKeysPresent() throws IOException {
        Properties en = loadBundle("/resources/CustomCodeBlock.properties");
        String[] required = {
            "jnt_customCodeBlock",
            "jnt_customCodeBlock.html",
            "jnt_customCodeBlock.css",
            "jnt_customCodeBlock.js",
            "jnt_customCodeBlock.html.ui.tooltip",
            "jnt_customCodeBlock.css.ui.tooltip",
            "jnt_customCodeBlock.js.ui.tooltip"
        };
        for (String key : required) {
            assertNotNull("Required key missing: " + key, en.getProperty(key));
        }
    }
}
