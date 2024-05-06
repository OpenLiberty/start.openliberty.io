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

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;

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

	public String getJsonData(String jsonUrl) throws Exception{
        URL url = new URL(jsonUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        con.disconnect();
		return parseJsonObject(new JSONObject(response.toString()));
    }

    private String parseJsonObject(JSONObject jsonObject) {
        JSONObject response = jsonObject.getJSONObject("response");
        JSONArray docs = response.getJSONArray("docs");
        JSONObject doc=docs.getJSONObject(0);
        String pluginVersion = doc.getString("v");
        return pluginVersion;
    }

	@GET
	@Produces("application/zip")
	@Path("start")
	public Response createAppZip(@Context HttpServletRequest req,
			@QueryParam("a") @Pattern(regexp = "([a-z]+\\-)*[a-z]+", message = "App name must be a-z characters separated by dashes") @Parameter(description = "App Name") String appName,
			@QueryParam("g") @Pattern(regexp = "[A-Za-z0-9_]+(\\.[A-Za-z0-9_]+)*", message = "Group name allows a-z, A-Z, 0-9 and underscores. Package names are separated by periods") @Parameter(description = "Base Package") String groupId,
			@JavaVersion @QueryParam("j") @Parameter(description = "Java SE Version") String javaVersion,
			@QueryParam("b") @Parameter(description = "Build System") BuildSystemType buildSystem,
			@JakartaEEVersion @QueryParam("e") @Parameter(description = "Java EE / Jakarta EE Version") String jakartaEEVersion,
			@MicroProfileVersion @QueryParam("m") @Parameter(description = "MicroProfile Version") String microProfileVersion)
			throws IOException {

		updateNLSStrings(req.getLocale());

		ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
		ZipArchiveOutputStream zipOut = new ZipArchiveOutputStream(bytesOut);

		boolean result = buildSystem.create().appName(appName).groupName(groupId).javaVersion(javaVersion)
				.jakartaEEVersion(jakartaEEVersion).microProfileVersion(microProfileVersion)
				.buildType(buildSystem.toString()).build(zipOut);

		zipOut.close();

		if (result) {
			return Response.ok(bytesOut.toByteArray())
					.header("Content-Disposition", "attachment; filename=\"" + appName + ".zip\"")
					.header("Content-Transfer-Encoding", "binary").build();
		} else {
			
			return Response.status(500).build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("start/info")
	public Response getInfo(@Context HttpServletRequest req) {
		System.out.println("Get info");
		updateNLSStrings(req.getLocale());
		return Response.ok(metadataJson).build();
	}

	String mavenUrl="https://search.maven.org/solrsearch/select?q=g:io.openliberty.tools+AND+a:liberty-maven-plugin&core=gav&rows=20&wt=json";
    String gradleUrl="https://search.maven.org/solrsearch/select?q=g:io.openliberty.tools+AND+a:liberty-gradle-plugin&core=gav&rows=20&wt=json";
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("start/plugin-versions")
    public Response getVersionFromJson() {
        try {
            String mavenVersion=getJsonData(mavenUrl);
            String gradleVersion=getJsonData(gradleUrl);
			return Response.ok(new JSONObject()
                               .put("mavenVersion", mavenVersion)
                               .put("gradleVersion", gradleVersion)
                               .toString())
                          .build();     
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(new JSONObject()
                                .put("status", "error")
                                .put("message", "Failed to fetch plugin versions")
                                .toString())
                        .build();
        }
    }
}
