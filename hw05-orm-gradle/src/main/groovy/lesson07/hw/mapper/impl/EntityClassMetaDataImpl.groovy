package lesson07.hw.mapper.impl

import groovy.transform.Canonical
import lesson07.hw.exception.EntityMetaDataException
import lesson07.hw.mapper.EntityClassMetaData

import java.lang.reflect.Constructor
import java.lang.reflect.Field

@Canonical
class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    Class<T> clazz

    Field idField
    List<Field> withoutIdFiles
    Constructor<T> constructor

    EntityClassMetaDataImpl(Class<T> clazz) {
        this.clazz = clazz
        this.constructor = findFirstConstructorWithoutparameters()
        def fields = clazz.getDeclaredFields() as List<Field>
        fields.each {
            if (it.getName() == findIdField().getName()){
                idField = it
            } else {
                withoutIdFiles.add(it)
            }
        }
    }

    def findIdField() {
        for (def field in clazz.getDeclaredFields()) {
            if(field.isAnnotationPresent(Id.class)) {
                return field
            }
        }
        throw new EntityMetaDataException("@id not found for class ${getName()}")
    }

    def findFirstConstructorWithoutparameters() {
        return constructor
    }

    @Override
    String getName() {
        return clazz.name
    }

    @Override
    List<Field> getAllFields() {
        return clazz.getDeclaredFields()
    }

    @Override
    List<Field> getFieldsWithoutId() {
        return withoutIdFiles
    }
}
