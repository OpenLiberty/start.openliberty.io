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
import io.openliberty.website.starter.validation.GradleVersion;

@Dependent
public class GradleVersionValidator extends AbstractEnumValidator<GradleVersion, String> {

    @Inject
    private StartMetadata metaData;

    public void initialize(GradleVersion constraintAnnotation) {
        super.init(constraintAnnotation.message(), metaData.getGradleVersion().values);
    }
}
