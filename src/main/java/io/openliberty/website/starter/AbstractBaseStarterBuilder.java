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
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import io.openliberty.website.starter.StarterBuilder;

public abstract class AbstractBaseStarterBuilder implements StarterBuilder{
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

    protected static void addDirectory(ZipOutputStream zipOut, String dirName) throws IOException {
        ZipEntry entry = new ZipEntry("/src/main/java/");
        zipOut.putNextEntry(entry);
        zipOut.closeEntry();
    }

    protected static void addFile(ZipOutputStream zipOut, String fileName, InputStream in) throws IOException {
        ZipEntry entry = new ZipEntry(fileName);
        zipOut.putNextEntry(entry);
        byte[] bytes = new byte[1024 * 4];
        int len;
        while ((len = in.read(bytes)) > 0) {
            zipOut.write(bytes, 0, len);
        }
        zipOut.closeEntry();
    }

    protected void addFileWithPropertyReplacement(ZipOutputStream zipOut, String fileName, String fileContent) throws IOException{
        ZipEntry entry = new ZipEntry(fileName);
        zipOut.putNextEntry(entry);

        for (Map.Entry<String, String> propEntry : properties.entrySet()) {
            fileContent = fileContent.replaceAll("\\$\\{" + propEntry.getKey() + "\\}", propEntry.getValue());
        }

        zipOut.write(fileContent.getBytes("utf-8"));
        zipOut.closeEntry();
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