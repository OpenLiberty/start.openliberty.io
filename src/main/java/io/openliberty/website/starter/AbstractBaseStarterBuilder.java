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
package io.openliberty.website.starter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import io.openliberty.website.starter.StarterBuilder;

public abstract class AbstractBaseStarterBuilder implements StarterBuilder {
    protected String appName;
    protected String groupId;
    protected String javaVersion;
    protected String jakartaEEVersion;
    protected String microProfileVersion;

    protected Map<String, String> properties = new HashMap<>();

    protected static final String SERVER_XML;

    static {
        String server = null;
        try (BufferedReader reader = new BufferedReader((new InputStreamReader(AbstractBaseStarterBuilder.class.getResourceAsStream("server.xml"), "utf-8")))) {
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("\r\n");
            }
            server = builder.toString();
        } catch (IOException ioe) {
            // ignoring
        }
        SERVER_XML = server; // TODO this doesn't deal with failure well, although failure is unlikely
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

        if ("7".equals(jakartaEEVersion)) {
            properties.put("jakartaEEGroupId", "javax");
            properties.put("jakartaEEArtifactId", "javaee-api");
            properties.put("jakartaEEVersion", "7.0.0");
            properties.put("jakartaEEFeature", "javaee-7.0");
        } else {
            properties.put("jakartaEEGroupId", "jakarta.platform");
            properties.put("jakartaEEArtifactId", "jakarta.jakartaee-api");
            properties.put("jakartaEEVersion", "8.0.0");
            properties.put("jakartaEEFeature", "jakartaee-8.0");
        }

        return this;
    }

    @Override
    public final StarterBuilder microProfileVersion(String microProfileVersion) {
        this.microProfileVersion = microProfileVersion;
        properties.put("microProfileVersion", microProfileVersion);
        return this;
    }

    protected static void addDirectory(ZipArchiveOutputStream zipOut, String dirName) throws IOException {
        ZipArchiveEntry entry = new ZipArchiveEntry("/src/main/java/");
        zipOut.putArchiveEntry(entry);
        zipOut.closeArchiveEntry();
    }

    protected static void addExecutableFile(ZipArchiveOutputStream zipOut, String fileName, InputStream in) throws IOException {
        ZipArchiveEntry entry = new ZipArchiveEntry(fileName);
        // rwxrw-r-- - 111110100 - 500
        entry.setUnixMode(500);
        addFile(zipOut, entry, in);
    }

    protected static void addFile(ZipArchiveOutputStream zipOut, String fileName, InputStream in) throws IOException {
        ZipArchiveEntry entry = new ZipArchiveEntry(fileName);
        // rw-rw-r-- - 110110100 - 436
        entry.setUnixMode(436);
        addFile(zipOut, entry, in);
    }

    private static void addFile(ZipArchiveOutputStream zipOut, ZipArchiveEntry entry, InputStream in) throws IOException {
        zipOut.putArchiveEntry(entry);
        byte[] bytes = new byte[1024 * 4];
        int len;
        while ((len = in.read(bytes)) > 0) {
            zipOut.write(bytes, 0, len);
        }
        zipOut.closeArchiveEntry();
    }

    protected void addFileWithPropertyReplacement(ZipArchiveOutputStream zipOut, String fileName, String fileContent) throws IOException{
        ZipArchiveEntry entry = new ZipArchiveEntry(fileName);
        zipOut.putArchiveEntry(entry);

        for (Map.Entry<String, String> propEntry : properties.entrySet()) {
            fileContent = fileContent.replaceAll("\\$\\{" + propEntry.getKey() + "\\}", propEntry.getValue());
        }

        zipOut.write(fileContent.getBytes("utf-8"));
        zipOut.closeArchiveEntry();
    }

    protected static String readFile(InputStream in) {
        try (BufferedReader reader = new BufferedReader((new InputStreamReader(in, "utf-8")))) {
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("\r\n");
            }
            return builder.toString();
        } catch (IOException ioe) {
            // ignoring
        }
        return null;
    }
}