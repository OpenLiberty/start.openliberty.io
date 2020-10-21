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
package io.openliberty.website.starter.validation.validator;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import io.openliberty.website.starter.NLS;
import io.openliberty.website.starter.metadata.StartMetadata;
import io.openliberty.website.starter.validation.JavaVersion;

@Dependent
public class JavaVersionValidator extends AbstractEnumValidator<JavaVersion, String> {

    @Inject
    private StartMetadata metaData;

    public void initialize(JavaVersion constraintAnnotation) {
        super.init(constraintAnnotation.messageKey(), metaData.getJavaVersion().values);
    }
}