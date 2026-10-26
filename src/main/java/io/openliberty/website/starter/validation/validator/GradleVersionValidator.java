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
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidatorContext;

import io.openliberty.website.starter.v2.V2StartMetadata;
import io.openliberty.website.starter.validation.GradleVersion;

@Dependent
public class GradleVersionValidator extends AbstractEnumValidator<GradleVersion, String> {

    @Inject
    private V2StartMetadata metaData;

    @Inject
    private HttpServletRequest request;

    public void initialize(GradleVersion constraintAnnotation) {
        super.init(constraintAnnotation.message(), metaData.getGradleVersion().values);
    }

    // gv is required when b=gradle; absent/empty is valid for Maven builds.
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (request != null && "gradle".equalsIgnoreCase(request.getParameter("b"))) {
            return super.isValid(value, context);
        }
        if (value == null || value.isEmpty()) {
            return true;
        }
        return super.isValid(value, context);
    }
}
