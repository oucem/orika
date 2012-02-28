/*
 * Orika - simpler, better and faster Java bean mapping
 * 
 * Copyright (C) 2011 Orika authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ma.glasnost.orika.metadata;

import java.util.Collections;
import java.util.Set;

import ma.glasnost.orika.Mapper;

public class ClassMap<A, B> {
    
    private final TypeHolder<A> aType;
    private final TypeHolder<B> bType;
    private final Set<FieldMap> fieldsMapping;
    private final Set<MapperKey> usedMappers;
    
    private final Mapper<A, B> customizedMapper;
    
    private final String[] constructorA;
    private final String[] constructorB;
    
    public ClassMap(TypeHolder<A> aType, TypeHolder<B> bType, Set<FieldMap> fieldsMapping, Mapper<A, B> customizedMapper, Set<MapperKey> usedMappers,
            String[] constructorA, String[] constructorB) {
        this.aType = aType;
        this.bType = bType;
        
        this.customizedMapper = customizedMapper;
        
        this.fieldsMapping = Collections.unmodifiableSet(fieldsMapping);
        this.usedMappers = Collections.unmodifiableSet(usedMappers);
        
        if (constructorA != null) {
            this.constructorA = constructorA.clone();
        } else {
            this.constructorA = null;
        }
        
        if (constructorB != null) {
            this.constructorB = constructorB.clone();
        } else {
            this.constructorB = null;
        }
    }
    
    public void addFieldMap(FieldMap fieldMap) {
        fieldsMapping.add(fieldMap);
    }
    
    public TypeHolder<?> getAType() {
        return aType;
    }
    
    public TypeHolder<?> getBType() {
        return bType;
    }
    
    public Set<FieldMap> getFieldsMapping() {
        return fieldsMapping;
    }
    
    public String getATypeName() {
        return aType.getSimpleName();
    }
    
    public String getBTypeName() {
        return bType.getSimpleName();
    }
    
    public Mapper<A, B> getCustomizedMapper() {
        return customizedMapper;
    }
    
    public String getMapperClassName() {
        // TODO This should be a strategy defined at the MapperGenerator level,
        // something like mapperClassNameStrategy.getMapperClassName(ClassMap
        // classMap)
        return "Orika" + bType.getSimpleName() + getATypeName() + "Mapper" + System.identityHashCode(this);
    }
    
    public String[] getConstructorA() {
        return constructorA;
    }
    
    public String[] getConstructorB() {
        return constructorB;
    }
    
    @Override
    public int hashCode() {
        int result = 31;
        result = result + (aType == null ? 0 : aType.hashCode());
        result = result + (bType == null ? 0 : bType.hashCode());
        return result;
    }
    
    public Set<MapperKey> getUsedMappers() {
        return usedMappers;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ClassMap<?, ?> other = (ClassMap<?, ?>) obj;
        if (aType == null) {
            if (other.aType != null) {
                return false;
            }
        } else if (!aType.equals(other.aType)) {
            return false;
        }
        if (bType == null) {
            if (other.bType != null) {
                return false;
            }
        } else if (!bType.equals(other.bType)) {
            return false;
        }
        return true;
    }
    
}