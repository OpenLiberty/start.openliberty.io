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

import io.openliberty.website.starter.gradle.GradleBuilder;
import io.openliberty.website.starter.maven.MavenBuilder;

public enum BuildSystemType {
    maven {
        public StarterBuilder create() {
            return new MavenBuilder();
        }
    }, gradle {
        public StarterBuilder create() {
            return new GradleBuilder();
        }
    }; 

    public abstract StarterBuilder create();
}