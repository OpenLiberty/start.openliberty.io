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

import io.openliberty.website.starter.validation.MicroProfileVersion;

public class MicroProfileVersionValidator implements ConstraintValidator<MicroProfileVersion, String> {

    private List<String> supportedVersion = Arrays.asList("1.0", "1.1", "1.2", "1.3", "1.4", "2.0", "2.1", "2.2", "3.0", "3.2", "3.3");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return supportedVersion.contains(value);
    }

}
