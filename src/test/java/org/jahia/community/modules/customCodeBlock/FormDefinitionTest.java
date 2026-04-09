package org.jahia.community.modules.customCodeBlock;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Validates the Content Editor form override for jnt:customCodeBlock.
 */
public class FormDefinitionTest {

    private static JsonNode root;

    @BeforeClass
    public static void loadJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = FormDefinitionTest.class.getResourceAsStream(
                "/META-INF/jahia-content-editor-forms/fieldsets/jcnt_customCodeBlock.json")) {
            assertNotNull("Form definition JSON not found", is);
            root = mapper.readTree(is);
        }
    }

    @Test
    public void jsonIsValid() {
        assertNotNull("JSON root must not be null", root);
    }

    @Test
    public void nameIsCorrectNodeType() {
        assertEquals("jnt:customCodeBlock", root.get("name").asText());
    }

    @Test
    public void fieldsArrayPresent() {
        assertTrue("fields must be an array", root.get("fields").isArray());
        assertFalse("fields must not be empty", root.get("fields").isEmpty());
    }

    @Test
    public void allExpectedFieldsPresent() {
        List<String> fieldNames = new ArrayList<>();
        for (JsonNode field : root.get("fields")) {
            fieldNames.add(field.get("name").asText());
        }
        assertTrue("html field missing", fieldNames.contains("html"));
        assertTrue("css field missing", fieldNames.contains("css"));
        assertTrue("js field missing", fieldNames.contains("js"));
    }

    @Test
    public void cssFieldHasCssMode() {
        for (JsonNode field : root.get("fields")) {
            if ("css".equals(field.get("name").asText())) {
                JsonNode options = field.get("selectorOptions");
                assertNotNull("css field must have selectorOptions", options);
                boolean found = false;
                for (JsonNode opt : options) {
                    if ("mode".equals(opt.get("name").asText()) && "css".equals(opt.get("value").asText())) {
                        found = true;
                    }
                }
                assertTrue("css field must have mode=css selectorOption", found);
                return;
            }
        }
        fail("css field not found");
    }

    @Test
    public void jsFieldHasJavascriptMode() {
        for (JsonNode field : root.get("fields")) {
            if ("js".equals(field.get("name").asText())) {
                JsonNode options = field.get("selectorOptions");
                assertNotNull("js field must have selectorOptions", options);
                boolean found = false;
                for (JsonNode opt : options) {
                    if ("mode".equals(opt.get("name").asText()) && "javascript".equals(opt.get("value").asText())) {
                        found = true;
                    }
                }
                assertTrue("js field must have mode=javascript selectorOption", found);
                return;
            }
        }
        fail("js field not found");
    }

    @Test
    public void allFieldsHaveCodeMirrorSelector() {
        for (JsonNode field : root.get("fields")) {
            String name = field.get("name").asText();
            assertEquals("Field " + name + " must use CodeMirror selector",
                    "CodeMirror", field.get("selectorType").asText());
        }
    }
}
