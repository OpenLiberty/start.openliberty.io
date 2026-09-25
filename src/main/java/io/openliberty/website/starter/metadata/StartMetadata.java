/*******************************************************************************
 * Copyright (c) 2020, 2026 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package io.openliberty.website.starter.metadata;

import javax.enterprise.context.ApplicationScoped;
import javax.json.bind.annotation.JsonbProperty;

import io.openliberty.website.starter.BuildSystemType;
import io.openliberty.website.starter.NLS;

@ApplicationScoped
public class StartMetadata {
    private SimpleStringMetadata appName = new SimpleStringMetadata(NLS.getMessage("appName"), "string", "app-name");
    private SimpleStringMetadata groupName = new SimpleStringMetadata(NLS.getMessage("basePackage"), "string",
            "com.demo");
    private EnumStringMetadata javaVersion = new EnumStringMetadata(NLS.getMessage("javaVersion"), "21",
            Constants.SUPPORTED_JAVA_VERSIONS);
    private EnumMetadata<BuildSystemType> buildSystem = new EnumMetadata<BuildSystemType>(NLS.getMessage("buildSystem"),
            BuildSystemType.maven, BuildSystemType.values());
    private EnumStringMetadata jakartaEEVersion = new EnumStringMetadataWithConstraint(
            NLS.getMessage("jakartaEEVersion"), "11.0", Constants.JAKARTA_EE_MICROPROFILE_COMPATIBILITIES,
            Constants.SUPPORTED_JAKARTAEE_VERSIONS);
    private EnumStringMetadata microProfileVersion = new EnumStringMetadata(NLS.getMessage("microProfileVersion"),
            "7.1", Constants.SUPPORTED_MICROPROFILE_VERSIONS);
    private EnumStringMetadata gradleVersion = new EnumStringMetadataWithConstraint(NLS.getMessage("gradleVersion"),
            "9", Constants.GRADLE_JAVA_COMPATIBILITIES, Constants.SUPPORTED_GRADLE_VERSIONS);

    public void updateDisplayStrings() {
        appName.updateString(NLS.getMessage("appName"));
        groupName.updateString(NLS.getMessage("basePackage"));
        javaVersion.updateString(NLS.getMessage("javaVersion"));
        buildSystem.updateString(NLS.getMessage("buildSystem"));
        jakartaEEVersion.updateString(NLS.getMessage("jakartaEEVersion"));
        microProfileVersion.updateString(NLS.getMessage("microProfileVersion"));
        gradleVersion.updateString(NLS.getMessage("gradleVersion"));
    }

    @JsonbProperty("a")
    public SimpleStringMetadata getAppName() {
        return appName;
    }

    @JsonbProperty("g")
    public SimpleStringMetadata getGroupName() {
        return groupName;
    }

    @JsonbProperty("b")
    public EnumMetadata<BuildSystemType> getBuildSystem() {
        return buildSystem;
    }

    @JsonbProperty("j")
    public EnumStringMetadata getJavaVersion() {
        return javaVersion;
    }

    @JsonbProperty("e")
    public EnumStringMetadata getJakartaEEVersion() {
        return jakartaEEVersion;
    }

    @JsonbProperty("m")
    public EnumStringMetadata getMicroProfileVersion() {
        return microProfileVersion;
    }

    @JsonbProperty("gv")
    public EnumStringMetadata getGradleVersion() {
        return gradleVersion;
    }
}
