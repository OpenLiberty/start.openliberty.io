package io.openliberty.website.starter.metadata;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.config.PropertyVisibilityStrategy;

public class MetadataVisibilityStrategy implements PropertyVisibilityStrategy {

    @Override
    public boolean isVisible(Field field) {
        return field.isAnnotationPresent(JsonbProperty.class);
    }

    @Override
    public boolean isVisible(Method method) {
        return method.isAnnotationPresent(JsonbProperty.class);
    }

}
