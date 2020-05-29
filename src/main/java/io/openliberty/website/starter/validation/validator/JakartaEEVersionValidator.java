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

import io.openliberty.website.starter.metadata.StartMetadata;
import io.openliberty.website.starter.validation.JakartaEEVersion;

@Dependent
public class JakartaEEVersionValidator extends AbstractEnumValidator<JakartaEEVersion, String> {

    @Inject
    private StartMetadata metaData;

    public void initialize(JakartaEEVersion constraintAnnotation) {
        super.init(constraintAnnotation.message(), metaData.getJakartaEEVersion().values);
    }
}