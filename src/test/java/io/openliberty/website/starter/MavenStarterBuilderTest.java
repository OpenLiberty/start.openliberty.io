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

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

public class MavenStarterBuilderTest {

    @Test
    public void testJakartaEE8() throws UnsupportedEncodingException, IOException, XmlPullParserException {
        MockZipOutputStream mockZip = MockZipOutputStream.create().capture("pom.xml").capture("src/main/liberty/config/server.xml");
        BuildSystemType.maven.create().appName("test").groupName("io.openliberty.demo").javaVersion("11")
                .jakartaEEVersion("8").microProfileVersion("3.3").build(mockZip);

        assertFilesPresent(mockZip);
        assertDependency(mockZip, "jakarta.platform", "jakarta.jakartaee-api", "8.0.0");
        assertDependency(mockZip, "org.eclipse.microprofile", "microprofile", "3.3");
        assertServerFeature(mockZip, "jakartaee-8.0");
    }

    @Test
    public void testJavaEE7() throws UnsupportedEncodingException, IOException, XmlPullParserException {
        MockZipOutputStream mockZip = MockZipOutputStream.create().capture("pom.xml").capture("src/main/liberty/config/server.xml");
        BuildSystemType.maven.create().appName("test").groupName("io.openliberty.demo").javaVersion("11")
                .jakartaEEVersion("7").microProfileVersion("3.3").build(mockZip);

        assertFilesPresent(mockZip);
        assertDependency(mockZip, "javax", "javaee-api", "7.0.0");
        assertDependency(mockZip, "org.eclipse.microprofile", "microprofile", "3.3");
        assertServerFeature(mockZip, "javaee-7.0");
    }

    private void assertServerFeature(MockZipOutputStream mockZip,
            String featureName)
            throws UnsupportedEncodingException {
        String serverXml = new String(mockZip.getCapturedFile("src/main/liberty/config/server.xml"), "utf-8");
        assertTrue(serverXml.contains("<feature>" + featureName + "</feature>"), "The feature: " + featureName + " was expected in the server.xml: \r\n" + serverXml);
    }

    private void assertDependency(MockZipOutputStream mockZip, String groupId, String artifactId, String version)
            throws UnsupportedEncodingException, IOException, XmlPullParserException {
        MavenXpp3Reader pomReader = new MavenXpp3Reader();
        Model pomModel = pomReader.read(new InputStreamReader(new ByteArrayInputStream(mockZip.getCapturedFile("pom.xml")), "utf-8"));
        List<Dependency> dependencies = pomModel.getDependencies();

        for (Dependency dep : dependencies) {
            if (groupId.equals(dep.getGroupId()) &&
                artifactId.equals(dep.getArtifactId()) &&
                version.equals(dep.getVersion())) {
                    return;
                }
        }

        throw new AssertionFailedError("Unable to find dependency: " + groupId + ":" + artifactId + ":" + version + " in dependencies list: " + dependencies);
    }

    private void assertFilesPresent(MockZipOutputStream mockZip) {
        mockZip.assertPresent("mvnw");
        mockZip.assertPresent("mvnw.cmd");
        mockZip.assertPresent(".mvn/wrapper/maven-wrapper.jar");
        mockZip.assertPresent(".mvn/wrapper/maven-wrapper.properties");
        mockZip.assertPresent("pom.xml");
        mockZip.assertPresent("src/main/liberty/config/server.xml");

    }
}