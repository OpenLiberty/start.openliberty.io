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

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import io.openliberty.website.starter.metadata.MetadataVisibilityStrategy;
import io.openliberty.website.starter.metadata.StartMetadata;
import io.openliberty.website.starter.validation.JakartaEEVersion;
import io.openliberty.website.starter.validation.JavaVersion;
import io.openliberty.website.starter.validation.MicroProfileVersion;

@ApplicationPath("/api")
@Path("/")
@RequestScoped
public class StartResource extends Application {

    @Inject
    private StartMetadata md;
    private String metadataJson;

    @PostConstruct
    public void init() {
        JsonbConfig cfg = new JsonbConfig();
        cfg.withPropertyVisibilityStrategy(new MetadataVisibilityStrategy());
        try (Jsonb json = JsonbBuilder.create(cfg)) {
            metadataJson = json.toJson(md);
        } catch (Exception e) {
            // ignore this
        }
    }

    @GET
    @Produces("application/zip")
    @Path("start")
    public Response createAppZip(@QueryParam("a") String appName,
                                 @QueryParam("g") String groupId, 
                                 @JavaVersion @QueryParam("j") String javaVersion,
                                 @QueryParam("b") BuildSystemType buildSystem,
                                 @JakartaEEVersion @QueryParam("e") String jakartaEEVersion,
                                 @MicroProfileVersion @QueryParam("m") String microProfileVersion) 
            throws IOException {

        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
        ZipArchiveOutputStream zipOut = new ZipArchiveOutputStream(bytesOut);

        boolean result = buildSystem.create().appName(appName)
                                    .groupName(groupId)
                                    .javaVersion(javaVersion)
                                    .jakartaEEVersion(jakartaEEVersion)
                                    .microProfileVersion(microProfileVersion)
                                    .buildType(buildSystem.toString())
                                    .build(zipOut); 

        zipOut.close();

        if (result) {
            return Response.ok(bytesOut.toByteArray())
                           .header("Content-Disposition", "attachment; filename=\"" + appName + ".zip\"")
                           .header("Content-Transfer-Encoding", "binary")
                           .build();
        } else {
            return Response.status(500).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("start/info")
    public Response getInfo() {
        return Response.ok(metadataJson).build();
    }
}