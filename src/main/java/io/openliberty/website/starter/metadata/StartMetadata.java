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
package io.openliberty.website.starter.metadata;

import javax.enterprise.context.ApplicationScoped;
import javax.json.bind.annotation.JsonbProperty;

import io.openliberty.website.starter.BuildSystemType;

@ApplicationScoped
public class StartMetadata {
    private SimpleStringMetadata appName = new SimpleStringMetadata("appName", "string", "appName");
    private SimpleStringMetadata groupName = new SimpleStringMetadata("groupName", "string", "com.demo");
    private EnumStringMetadata javaVersion = new EnumStringMetadata("javaVersion", "8", Constants.SUPPORTED_JAVA_VERSIONS);
    private EnumMetadata<BuildSystemType> buildSystem = new EnumMetadata<BuildSystemType>("buildSystem", BuildSystemType.maven, BuildSystemType.values());
    private EnumStringMetadata jakartaEEVersion = new EnumStringMetadata("jakartaEEVersion", "8", Constants.SUPPORTED_JAKARTAEE_VERSIONS);
    private EnumStringMetadata microProfileVersion  = new EnumStringMetadata("microProfileVersion", "3.3", Constants.SUPPORTED_MICROPROFILE_VERSIONS);

    @JsonbProperty("appName")
    public SimpleStringMetadata getAppName() { return appName; }
    @JsonbProperty("groupName")
    public SimpleStringMetadata getGroupName() { return groupName; }
    @JsonbProperty("buildSystem")
    public EnumMetadata<BuildSystemType> getBuildSystem() { return buildSystem; }
    @JsonbProperty("javaVersion")
    public EnumStringMetadata getJavaVersion() { return javaVersion; }
    @JsonbProperty("jakartaEEVersion")
    public EnumStringMetadata getJakartaEEVersion() { return jakartaEEVersion; }
    @JsonbProperty("microProfileVersion")
    public EnumStringMetadata getMicroProfileVersion() { return microProfileVersion; }
}