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

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import io.openliberty.website.starter.validation.JakartaEEVersion;

public class JakartaEEVersionValidator implements ConstraintValidator<JakartaEEVersion, String> {

    private List<Integer> supportedVersion = Arrays.asList(7, 8);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            int version = Integer.valueOf(value);

            return supportedVersion.contains(version);
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

}
