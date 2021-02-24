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
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Pattern;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

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
	private JsonbConfig cfg;

	@PostConstruct
	public void init() {
		System.out.println("Initialize data");
		cfg = new JsonbConfig();
		cfg.withPropertyVisibilityStrategy(new MetadataVisibilityStrategy());
		try (Jsonb json = JsonbBuilder.create(cfg)) {
			metadataJson = json.toJson(md);
		} catch (Exception e) {
			// ignore this
			e.printStackTrace();
		}
	}
	
	public void updateNLSStrings(Locale locale) {	
		System.out.println("Updating NLS Strings for locale: " + locale);		
		try (Jsonb json = JsonbBuilder.create(cfg)) {			
			NLS.loadBundle(locale);
			md.updateDisplayStrings();
			cfg.withPropertyVisibilityStrategy(new MetadataVisibilityStrategy());
			metadataJson = json.toJson(md);
		} catch (Exception e) {			
			// ignore this
			e.printStackTrace();
		}
	}

	@GET
	@Produces("application/zip")
	@Path("start")
	public Response createAppZip(@Context HttpServletRequest req, @QueryParam("a") @Pattern(regexp = "^[A-Za-z0-9]+$", message="Use alphanumeric characters only") @Parameter(description = "App Name") String appName,
			@QueryParam("g") @Pattern(regexp = "^[a-z\\.]*[a-z]$", message = "Group name must be lowercase letters separated by periods.") @Parameter(description = "Base Package") String groupId,
			@JavaVersion @QueryParam("j") @Parameter(description = "Java SE Version") String javaVersion,
			@QueryParam("b") @Parameter(description = "Build System") BuildSystemType buildSystem,
			@JakartaEEVersion @QueryParam("e") @Parameter(description = "Java EE / Jakarta EE Version") String jakartaEEVersion,
			@MicroProfileVersion @QueryParam("m") @Parameter(description = "MicroProfile Version") String microProfileVersion)
			throws IOException {
		
		updateNLSStrings(req.getLocale());
		
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
	public Response getInfo(@Context HttpServletRequest req) {
		System.out.println("Get info");
		System.out.println(metadataJson);
		updateNLSStrings(req.getLocale());
		return Response.ok(metadataJson).build();
	}
}