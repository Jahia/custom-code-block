package org.jahia.community.modules.customCodeBlock;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.junit.Assert.*;

/**
 * Validates the CND node type definitions for the customCodeBlock module.
 */
public class CndDefinitionTest {

    private static String cnd;

    @BeforeClass
    public static void loadCnd() throws IOException {
        try (InputStream is = CndDefinitionTest.class.getResourceAsStream("/META-INF/definitions.cnd")) {
            assertNotNull("definitions.cnd not found on classpath", is);
            try (Scanner scanner = new Scanner(is, StandardCharsets.UTF_8.name())) {
                cnd = scanner.useDelimiter("\\A").next();
            }
        }
    }

    // --- Namespace declarations ---

    @Test
    public void jntNamespaceDeclared() {
        assertTrue("jnt namespace must be declared", cnd.contains("<jnt = 'http://www.jahia.org/jahia/nt/1.0'>"));
    }

    @Test
    public void jmixNamespaceDeclared() {
        assertTrue("jmix namespace must be declared", cnd.contains("<jmix = 'http://www.jahia.org/jahia/mix/1.0'>"));
    }

    // --- jmix:advancedContent ---

    @Test
    public void advancedContentMixinDefined() {
        assertTrue("jmix:advancedContent must be defined", cnd.contains("[jmix:advancedContent]"));
    }

    @Test
    public void advancedContentExtendsDroppableContent() {
        String line = lineContaining("[jmix:advancedContent]");
        assertTrue("jmix:advancedContent must extend jmix:droppableContent", line.contains("jmix:droppableContent"));
    }

    @Test
    public void advancedContentExtendsAccessControllable() {
        String line = lineContaining("[jmix:advancedContent]");
        assertTrue("jmix:advancedContent must extend jmix:accessControllableContent",
                line.contains("jmix:accessControllableContent"));
    }

    @Test
    public void advancedContentIsMixin() {
        String line = lineContaining("[jmix:advancedContent]");
        assertTrue("jmix:advancedContent must be declared as mixin", line.contains("mixin"));
    }

    // --- jnt:customCodeBlock ---

    @Test
    public void customCodeBlockNodeTypeDefined() {
        assertTrue("jnt:customCodeBlock must be defined", cnd.contains("[jnt:customCodeBlock]"));
    }

    @Test
    public void customCodeBlockExtendsJntContent() {
        String line = lineContaining("[jnt:customCodeBlock]");
        assertTrue("jnt:customCodeBlock must extend jnt:content", line.contains("jnt:content"));
    }

    @Test
    public void customCodeBlockExtendsAdvancedContent() {
        String line = lineContaining("[jnt:customCodeBlock]");
        assertTrue("jnt:customCodeBlock must extend jmix:advancedContent", line.contains("jmix:advancedContent"));
    }

    // --- Properties ---

    @Test
    public void htmlPropertyDeclaredAsString() {
        String line = propertyLine("html");
        assertNotNull("html property must be declared", line);
        assertTrue("html must be of type string", line.contains("(string)"));
    }

    @Test
    public void htmlPropertyIsI18n() {
        String line = propertyLine("html");
        assertNotNull("html property must be declared", line);
        assertTrue("html property must be i18n", line.contains("i18n"));
    }

    @Test
    public void cssPropertyDeclaredAsString() {
        String line = propertyLine("css");
        assertNotNull("css property must be declared", line);
        assertTrue("css must be of type string", line.contains("(string)"));
    }

    @Test
    public void cssPropertyIsNotI18n() {
        String line = propertyLine("css");
        assertNotNull("css property must be declared", line);
        assertFalse("css property must NOT be i18n", line.contains("i18n"));
    }

    @Test
    public void jsPropertyDeclaredAsString() {
        String line = propertyLine("js");
        assertNotNull("js property must be declared", line);
        assertTrue("js must be of type string", line.contains("(string)"));
    }

    @Test
    public void jsPropertyIsNotI18n() {
        String line = propertyLine("js");
        assertNotNull("js property must be declared", line);
        assertFalse("js property must NOT be i18n", line.contains("i18n"));
    }

    // --- Helpers ---

    private String lineContaining(String token) {
        for (String line : cnd.split("\\r?\\n")) {
            if (line.contains(token)) return line;
        }
        return "";
    }

    private String propertyLine(String propertyName) {
        for (String line : cnd.split("\\r?\\n")) {
            String trimmed = line.trim();
            if (trimmed.startsWith("- " + propertyName + " ") || trimmed.startsWith("- " + propertyName + "\t")) {
                return trimmed;
            }
        }
        return null;
    }
}
