/*******************************************************************************
 * Copyright (c) 2020 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package io.openliberty.website.starter.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.json.bind.JsonbBuilder;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import io.openliberty.website.starter.NLS;
import io.openliberty.website.starter.StarterBuilder;
import io.openliberty.website.starter.metadata.TemplateMetadata;

public class StarterBuilderImpl implements StarterBuilder {
    protected String appName;
    protected String groupId;
    protected String javaVersion;
    protected String jakartaEEVersion;
    protected String microProfileVersion;

    protected Map<String, String> properties = new HashMap<>();

    protected List<String> requestedTemplates = new ArrayList<>();

    private static final Configuration cfg;
    private static Map<String, List<TemplateMetadata>> templates;

    static {
        cfg = new Configuration(Configuration.VERSION_2_3_30);
        cfg.setClassForTemplateLoading(StarterBuilderImpl.class, "/templates/");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.DEBUG_HANDLER);
        cfg.setLogTemplateExceptions(true);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);

        templates = JsonbBuilder.create().fromJson(
                StarterBuilderImpl.class.getResourceAsStream("/templates/templates.json"),
                new HashMap<String, List<TemplateMetadata>>() {
                }.getClass().getGenericSuperclass());
    }

    public StarterBuilderImpl() {
        requestedTemplates.add("server");
        requestedTemplates.add("docker");
        requestedTemplates.add("rest");
        requestedTemplates.add("readme");
    }

    @Override
    public final StarterBuilder appName(String appName) {
        this.appName = appName;
        properties.put("appName", appName);
        return this;
    }

    @Override
    public final StarterBuilder groupName(String groupId) {
        this.groupId = groupId;
        properties.put("groupName", groupId);
        properties.put("basePackageName", groupId);
        properties.put("basePackagePath", groupId.replaceAll("\\.", "/"));
        return this;
    }

    @Override
    public final StarterBuilder javaVersion(String javaVersion) {
        this.javaVersion = javaVersion;
        properties.put("javaVersion", javaVersion);
        return this;
    }

    @Override
    public final StarterBuilder jakartaEEVersion(String jakartaEEVersion) {
        this.jakartaEEVersion = jakartaEEVersion;

        properties.put("jakartaEEBasePackageName", "javax");

        if ("7".equals(jakartaEEVersion)) {
            properties.put("jakartaEEGroupId", "javax");
            properties.put("jakartaEEArtifactId", "javaee-api");
            properties.put("jakartaEEVersion", "7.0.0");
            properties.put("jakartaEEFeature", "javaee-7.0");
        }
        else if("8".equals(jakartaEEVersion)) {
            properties.put("jakartaEEGroupId", "jakarta.platform");
            properties.put("jakartaEEArtifactId", "jakarta.jakartaee-api");
            properties.put("jakartaEEVersion", "8.0.0");
            properties.put("jakartaEEFeature", "jakartaee-8.0");
        } 
        else {
            properties.put("jakartaEEGroupId", "jakarta.platform");
            properties.put("jakartaEEArtifactId", "jakarta.jakartaee-api");
            properties.put("jakartaEEVersion", "9.1.0");
            properties.put("jakartaEEFeature", "jakartaee-9.1");
            properties.put("jakartaEEBasePackageName", "jakarta");
        }

        return this;
    }

    @Override
    public final StarterBuilder microProfileVersion(String microProfileVersion) {
        this.microProfileVersion = microProfileVersion;
        if ("4.0".equals(microProfileVersion)) {
            properties.put("microProfilePomVersion", "4.0.1");
        } else {
            properties.put("microProfilePomVersion", microProfileVersion);
        }
        properties.put("microProfileVersion", microProfileVersion);
        return this;
    }

    @Override
    public final StarterBuilder template(String templateName) {
        requestedTemplates.add(templateName);
        return this;
    }

    @Override
    public final StarterBuilder buildType(String buildSystem) {
        properties.put("buildType", buildSystem.toLowerCase());
        if ("maven" == buildSystem) {
            properties.put("buildPath", "target/");
        } else if ("gradle" == buildSystem) {
            properties.put("buildPath", "build/libs/");
        }
        return this;
    }

    @Override
    public final boolean build(ZipArchiveOutputStream zipOut) {
        try {
            addDirectory(zipOut, properties.get("basePackagePath") + "/");
            for (String templateName : requestedTemplates) {
                List<TemplateMetadata> templateFiles = templates.get(templateName);
                for (TemplateMetadata template : templateFiles) {
                    String resolvedFileName = resolve(template.fileName);
                    if (template.process) {
                        addFileWithPropertyReplacement(zipOut, template.template, resolvedFileName);
                    } else if (template.executable) {
                        addExecutableFile(zipOut, template.template, resolvedFileName);
                    } else {
                        addFile(zipOut, template.template, resolvedFileName);
                    }
                }
            }
            return true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return false;
    }

    private String resolve(String fileName) {
        String resolvedFileName = fileName;
        if (fileName.contains("${")) {
            StringBuilder builder = new StringBuilder();
            String[] parts = fileName.split("\\$\\{");
            for (String part : parts) {
                int index = part.indexOf('}');
                if (index == -1) {
                    builder.append(part);
                } else {
                    String key = part.substring(0, index);
                    String remainder = part.substring(index + 1);
                    String replacement = properties.get(key);
                    if (replacement == null) {
                        builder.append("${");
                        builder.append(key);
                        builder.append("}");
                    } else {
                        builder.append(properties.get(key));
                    }
                    builder.append(remainder);
                }
            }
            resolvedFileName = builder.toString();
        }
        return resolvedFileName;
    }

    private void addFile(ZipArchiveOutputStream zipOut, String template, String fileName) throws IOException {
        addFile(zipOut, new ZipArchiveEntry(fileName), openTemplateFile(template));
    }

    private void addExecutableFile(ZipArchiveOutputStream zipOut, String template, String fileName) throws IOException {
        ZipArchiveEntry entry = new ZipArchiveEntry(fileName);
        // rwxrw-r-- - 111110100 - 500
        entry.setUnixMode(500);
        addFile(zipOut, entry, openTemplateFile(template));
    }

    private InputStream openTemplateFile(String template) {
        return StarterBuilderImpl.class.getResourceAsStream("/templates/" + template);
    }

    protected static void addDirectory(ZipArchiveOutputStream zipOut, String dirName) throws IOException {
        ZipArchiveEntry entry = new ZipArchiveEntry("/src/main/java/" + dirName + "/");
        zipOut.putArchiveEntry(entry);
        zipOut.closeArchiveEntry();
    }

    private static void addFile(ZipArchiveOutputStream zipOut, ZipArchiveEntry entry, InputStream in)
            throws IOException {
        zipOut.putArchiveEntry(entry);
        byte[] bytes = new byte[1024 * 4];
        int len;
        while ((len = in.read(bytes)) > 0) {
            zipOut.write(bytes, 0, len);
        }
        zipOut.closeArchiveEntry();
    }

    protected void addFileWithPropertyReplacement(ZipArchiveOutputStream zipOut, String templateName, String fileName)
            throws IOException {
        Template template = cfg.getTemplate(templateName);

        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
        Writer write = new OutputStreamWriter(bytesOut);

        try {
            template.process(properties, write);
        } catch (TemplateException te) {
            throw new IOException(NLS.getMessage("templateProcessError"), te);
        }

        ZipArchiveEntry entry = new ZipArchiveEntry(fileName);
        zipOut.putArchiveEntry(entry);

        zipOut.write(bytesOut.toByteArray());
        zipOut.closeArchiveEntry();
    }

}