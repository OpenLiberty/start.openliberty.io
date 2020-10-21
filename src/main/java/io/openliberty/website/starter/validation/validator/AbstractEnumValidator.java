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

import java.lang.annotation.Annotation;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import io.openliberty.website.starter.NLS;

public abstract class AbstractEnumValidator<A extends Annotation, T> implements ConstraintValidator<A, T> {

    private List<T> values;
    private String messageKey;
    private String template;

    @Override
    public boolean isValid(T value, ConstraintValidatorContext context) {
    	// Updates validation string with the right NLS resource bundle
    	this.template = NLS.getMessage(messageKey).replaceAll("\\{permittedValues\\}",values.toString());
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(template).addConstraintViolation();
        return values.contains(value);
    }

    public void init(String messageKey, List<T> values) {
    	this.messageKey = messageKey;
        this.values = values;
        this.template = NLS.getMessage(messageKey).replaceAll("\\{permittedValues\\}",values.toString());
    }
}