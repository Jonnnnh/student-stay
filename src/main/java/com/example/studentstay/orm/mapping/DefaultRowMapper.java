package com.example.studentstay.orm.mapping;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultRowMapper<T> implements RowMapper<T> {

    private final Class<T> mappedClass;
    private final Map<String, PropertyDescriptor> props;

    private static final Map<Class<?>, Map<String, PropertyDescriptor>> cache = new ConcurrentHashMap<>();

    public DefaultRowMapper(Class<T> mappedClass) {
        this.mappedClass = mappedClass;
        this.props = cache.computeIfAbsent(mappedClass, cls -> {
            try {
                Map<String, PropertyDescriptor> m = new HashMap<>();
                for (PropertyDescriptor pd : Introspector.getBeanInfo(cls).getPropertyDescriptors()) {
                    if (pd.getWriteMethod() != null) {
                        m.put(pd.getName().toLowerCase(), pd);
                    }
                }
                return m;
            } catch (Exception e) {
                throw new RuntimeException("Не удалось инициализировать DefaultRowMapper для " + cls, e);
            }
        });
    }

    @Override
    public T mapRow(ResultSet rs) throws SQLException {
        try {
            T instance = mappedClass.getDeclaredConstructor().newInstance();
            ResultSetMetaData md = rs.getMetaData();
            int cols = md.getColumnCount();

            for (int i = 1; i <= cols; i++) {
                String column = md.getColumnLabel(i);
                Object value = rs.getObject(i);

                String propName = toCamelCase(column);

                PropertyDescriptor pd = props.get(propName.toLowerCase());
                if (pd != null) {
                    Object converted = convertType(value, pd.getPropertyType());
                    pd.getWriteMethod().invoke(instance, converted);
                } else {
                    try {
                        Field f = mappedClass.getDeclaredField(propName);
                        if (!Modifier.isStatic(f.getModifiers())) {
                            f.setAccessible(true);
                            Object converted = convertType(value, f.getType());
                            f.set(instance, converted);
                        }
                    } catch (NoSuchFieldException ignored) {
                    }
                }
            }
            return instance;
        } catch (SQLException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SQLException("Ошибка маппинга строки в " + mappedClass.getSimpleName(), ex);
        }
    }

    private static String toCamelCase(String snake) {
        StringBuilder sb = new StringBuilder();
        boolean up = false;
        for (char c : snake.toCharArray()) {
            if (c == '_' || c == '-') {
                up = true;
            } else if (up) {
                sb.append(Character.toUpperCase(c));
                up = false;
            } else {
                sb.append(Character.toLowerCase(c));
            }
        }
        return sb.toString();
    }

    private static Object convertType(Object value, Class<?> targetType) {
        if (value == null) {
            return null;
        }
        if (targetType.isAssignableFrom(value.getClass())) {
            return value;
        }
        if (value instanceof Number) {
            Number n = (Number) value;
            if (targetType == Integer.class || targetType == int.class) {
                return n.intValue();
            } else if (targetType == Long.class || targetType == long.class) {
                return n.longValue();
            } else if (targetType == Double.class || targetType == double.class) {
                return n.doubleValue();
            } else if (targetType == Float.class || targetType == float.class) {
                return n.floatValue();
            }
        }
        if (value instanceof java.sql.Date && targetType == LocalDate.class) {
            return ((java.sql.Date) value).toLocalDate();
        }
        if (value instanceof java.sql.Timestamp) {
            Timestamp ts = (Timestamp) value;
            if (targetType == LocalDateTime.class) {
                return ts.toLocalDateTime();
            }
            if (targetType == OffsetDateTime.class) {
                return ts.toInstant().atOffset(ZoneId.systemDefault().getRules().getOffset(ts.toInstant()));
            }
        }
        if (value instanceof java.sql.Time && targetType == LocalTime.class) {
            return ((java.sql.Time) value).toLocalTime();
        }
        return value;
    }

    public static <T> T map(ResultSet rs, Class<T> cls) throws SQLException {
        return new DefaultRowMapper<>(cls).mapRow(rs);
    }
}