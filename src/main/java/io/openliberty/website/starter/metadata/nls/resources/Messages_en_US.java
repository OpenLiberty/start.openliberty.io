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

package io.openliberty.website.starter.metadata.nls.resources;

public class Messages_en_US extends java.util.ListResourceBundle
{
   public Object[][] getContents() {
       return resources;
   }
   private final static Object[][] resources= { 
      { "appName", "App Name" },
      { "basePackage", "Base Package"},
      { "buildSystem", "Build System"},
      { "jakartaEEVersion", "Java EE / Jakarta EE Version"},
      { "javaVersion", "Java SE Version"},
      { "microProfileVersion", "MicroProfile Version"},
      { "templateProcessError", "Failed to process template"}
   };
}
