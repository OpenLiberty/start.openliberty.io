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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

public class MockZipOutputStream extends ZipArchiveOutputStream {

    private List<String> fileNames = new ArrayList<>();
    private Map<String, ByteArrayOutputStream> capturedFiles = new HashMap<>();
    private List<String> filesToCapture = new ArrayList<>();
    private ByteArrayOutputStream bytesOut;

    public MockZipOutputStream() {
        super(new ByteArrayOutputStream());
    }

    @Override
    public void putArchiveEntry(ArchiveEntry e) {
        String name = e.getName();
        fileNames.add(name);
        if (filesToCapture.contains(name)) {
            bytesOut = new ByteArrayOutputStream();
            capturedFiles.put(name, bytesOut);
        } else if (bytesOut != null) {
            bytesOut = null;
        }
    }

    @Override
    public void closeArchiveEntry() throws IOException {
        bytesOut = null;
    }

    @Override
    public void write(byte[] b, int off, int len) {
        if (bytesOut != null) {
            bytesOut.write(b, off, len);
        }
    }

    public void assertPresent(String fileName) {
        assertTrue(fileNames.contains(fileName),
                "The file " + fileName + " was not added to the zip. Files added: " + fileNames);
    }

    public MockZipOutputStream capture(String fileName) {
        filesToCapture.add(fileName);
        return this;
    }

    public byte[] getCapturedFile(String fileName) {
        ByteArrayOutputStream bytesOut = capturedFiles.get(fileName);
        if (bytesOut != null) {
            return bytesOut.toByteArray();
        }

        return new byte[0];
    }

    public static MockZipOutputStream create() {
        return new MockZipOutputStream();
    }
}