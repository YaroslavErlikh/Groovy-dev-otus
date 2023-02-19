package lesson07.hw.mapper.impl

import groovy.transform.Canonical
import lesson07.hw.exception.EntityMetaDataException
import lesson07.hw.mapper.EntityClassMetaData
import lesson07.hw.mapper.EntitySQLMetaData

@Canonical
class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData {

    private final EntityClassMetaData<T> entityClassMetaData
    private final selectAll

    EntitySQLMetaDataImpl(entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData
        selectAll = "SELECT * FROM ${entityClassMetaData.getName()}"
    }

    @Override
    String getSelectAllSql() {
        return selectAll
    }

    @Override
    String getSelectByIdSql(long id) {
        return "SELECT * FROM ${entityClassMetaData.getName()} WHERE ${entityClassMetaData.getIdField()} = ${id}"
    }

    @Override
    String getInsertSql(Map<String, String> values) {
        def fieldsWithoutId = entityClassMetaData.fieldsWithoutId
        if (fieldsWithoutId.size() != values.size()) {
            throw new EntityMetaDataException("Fields count do not match to insert class ${entityClassMetaData.getName()}")
        }
        return "INSERT INTO ${entityClassMetaData.getName()}(" +
                "${String.join(",", fieldsWithoutId.collect {it.name})}" +
                ") " +
                "VALUES (" +
                "${String.join(",", values)}" +
                ")"
    }

    @Override
    String getUpdateSql(Map<String, String> fields, long id) {
        return "UPDATE ${entityClassMetaData.getName()} " +
                "SET ${String.join(",", fields.collect {"${it.key}=${it.value}"})} " +
                "WHERE id=${id}"
    }
}
