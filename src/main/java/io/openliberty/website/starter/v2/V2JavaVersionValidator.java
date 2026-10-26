/*******************************************************************************
 * Copyright (c) 2020, 2026 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package io.openliberty.website.starter.v2;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import io.openliberty.website.starter.validation.validator.AbstractEnumValidator;

@Dependent
public class V2JavaVersionValidator extends AbstractEnumValidator<V2JavaVersion, String> {

    @Inject
    private V2StartMetadata metaData;

    public void initialize(V2JavaVersion constraintAnnotation) {
        super.init(constraintAnnotation.message(), metaData.getJavaVersion().values);
    }
}
