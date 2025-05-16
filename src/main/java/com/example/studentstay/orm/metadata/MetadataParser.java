package com.example.studentstay.orm.metadata;

import com.example.studentstay.orm.annotation.Entity;
import com.example.studentstay.orm.annotation.Id;
import com.example.studentstay.orm.annotation.Transient;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;

public class MetadataParser {

    public static EntityMetadata parse(Class<?> cls) {
        Entity entAnn = cls.getAnnotation(Entity.class);
        if (entAnn == null) {
            throw new IllegalArgumentException("Класс " + cls.getName() + " не помечен @Entity");
        }

        String table = entAnn.name().isEmpty()
                ? cls.getSimpleName().toLowerCase()
                : entAnn.name();

        Field idField = null;
        for (Field f : cls.getDeclaredFields()) {
            if (f.isAnnotationPresent(Id.class)) {
                idField = f;
                break;
            }
        }
        if (idField == null) {
            throw new IllegalArgumentException("В классе " + cls.getName() + " нет поля с @Id");
        }

        Map<String, PropertyMetadata> props = new LinkedHashMap<>();
        for (Field f : cls.getDeclaredFields()) {
            int mods = f.getModifiers();
            if (Modifier.isStatic(mods)) continue;
            if (Modifier.isTransient(mods)) continue;
            if (f.isAnnotationPresent(Transient.class)) continue;
            boolean isId = f.equals(idField);
            String column = toSnakeCase(f.getName());
            props.put(f.getName(), new PropertyMetadata(f, column, isId));
        }

        PropertyMetadata idPropMeta = props.get(idField.getName());
        return new EntityMetadata(cls, table, idPropMeta, props);
    }

    private static String toSnakeCase(String camel) {
        StringBuilder sb = new StringBuilder();
        for (char c : camel.toCharArray()) {
            if (Character.isUpperCase(c)) {
                sb.append('_').append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}