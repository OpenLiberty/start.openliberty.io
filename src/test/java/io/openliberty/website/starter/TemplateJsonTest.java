package io.openliberty.website.starter;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.json.bind.JsonbBuilder;

import org.junit.jupiter.api.Test;

import io.openliberty.website.starter.metadata.TemplateMetadata;

public class TemplateJsonTest {
    @Test
    public void testAllFilesExist() {
        Map<String, List<TemplateMetadata>> templates = JsonbBuilder.create().fromJson(TemplateJsonTest.class.getResourceAsStream("/templates/templates.json"), new HashMap<String, List<TemplateMetadata>>(){}.getClass().getGenericSuperclass());

        for (Map.Entry<String, List<TemplateMetadata>> entry : templates.entrySet()) {
            String templateGroup = entry.getKey();
            for (TemplateMetadata metadata : entry.getValue()) {
                assertNotNull(TemplateJsonTest.class.getResourceAsStream("/templates/" + metadata.template), "The template file " + metadata.template + " referenced from template group " + templateGroup + " does not exist.");
            }
        }
    }
}