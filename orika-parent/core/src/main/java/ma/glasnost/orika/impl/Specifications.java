package ma.glasnost.orika.impl;

import java.math.BigDecimal;
import java.util.Date;

import ma.glasnost.orika.impl.util.CollectionUtil;
import ma.glasnost.orika.metadata.FieldMap;

public abstract class Specifications {
    
    public interface Specification {
        boolean apply(FieldMap fieldMap);
    }
    
    public static Specification immutable() {
        return IS_IMMUTABLE;
    }
    
    public static Specification compatibleTypes() {
        return HAVE_COMPATIBLE_TYPES;
    }
    
    public static Specification anArray() {
        return IS_ARRAY;
    }
    
    public static Specification aCollection() {
        return IS_COLLECTION;
    }
    
    public static Specification aPrimitive() {
        return IS_PRIMITIVE;
    }
    
    static final Specification IS_IMMUTABLE = new Specification() {
        
        public boolean apply(FieldMap fieldMap) {
            return CollectionUtil.equalsAny(fieldMap.getSource().getType(), String.class, Integer.class, Long.class, Boolean.class,
                    Character.class, Byte.class, Double.class, Float.class, BigDecimal.class, Integer.TYPE, Boolean.TYPE, Long.TYPE,
                    Float.TYPE, Double.TYPE, Character.TYPE, Date.class, java.sql.Date.class);
        }
    };
    
    static final Specification HAVE_COMPATIBLE_TYPES = new Specification() {
        
        public boolean apply(FieldMap fieldMap) {
            return fieldMap.getDestination().isAssignableFrom(fieldMap.getSource());
        }
    };
    
    static final Specification IS_ARRAY = new Specification() {
        
        public boolean apply(FieldMap fieldMap) {
            if (fieldMap.getDestination().isArray() && (fieldMap.getSource().isArray() || fieldMap.getSource().isCollection())) {
                return true;
            }
            return false;
        }
    };
    
    static final Specification IS_COLLECTION = new Specification() {
        
        public boolean apply(FieldMap fieldMap) {
            return (fieldMap.getSource().isArray() || fieldMap.getSource().isCollection()) && fieldMap.getDestination().isCollection();
        }
    };
    static final Specification IS_PRIMITIVE = new Specification() {
        
        public boolean apply(FieldMap fieldMap) {
            return fieldMap.getSource().getType().isPrimitive() || fieldMap.getDestination().getType().isPrimitive();
        }
    };
    
}