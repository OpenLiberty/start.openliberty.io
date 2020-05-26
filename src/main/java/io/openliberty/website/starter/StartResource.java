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
import java.util.zip.ZipOutputStream;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import io.openliberty.website.starter.validation.JakartaEEVersion;
import io.openliberty.website.starter.validation.JavaVersion;
import io.openliberty.website.starter.validation.MicroProfileVersion;

@ApplicationPath("/api")
@Path("/")
@RequestScoped
public class StartResource extends Application {

    @GET
    @Produces("application/zip")
    @Path("start")
    public Response createAppZip(@QueryParam("appName") String appName,
                                 @QueryParam("groupName") String groupId, 
                                 @JavaVersion @QueryParam("javaVersion") String javaVersion,
                                 @QueryParam("buildSystem") BuildSystemType buildSystem,
                                 @JakartaEEVersion @QueryParam("jakartaEEVersion") String jakartaEEVersion,
                                 @MicroProfileVersion @QueryParam("microProfileVersion") String microProfileVersion) 
            throws IOException {

        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
        ZipOutputStream zipOut = new ZipOutputStream(bytesOut);

        boolean result = buildSystem.create().appName(appName)
                                    .groupName(groupId)
                                    .javaVersion(javaVersion)
                                    .jakartaEEVersion(jakartaEEVersion)
                                    .microProfileVersion(microProfileVersion)
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
}