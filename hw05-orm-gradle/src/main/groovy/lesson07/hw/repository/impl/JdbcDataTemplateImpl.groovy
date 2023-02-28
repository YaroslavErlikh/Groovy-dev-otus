package lesson07.hw.repository.impl

import groovy.transform.Canonical
import lesson07.hw.mapper.EntityClassMetaData
import lesson07.hw.mapper.EntitySQLMetaData
import lesson07.hw.repository.DataTemplate
import lesson07.hw.repository.DbExecutor

@Canonical
class JdbcDataTemplateImpl<T> implements DataTemplate<T> {

    private DbExecutor dbExecutor
    private EntitySQLMetaData entitySQLMetaData
    private EntityClassMetaData entityClassMetaData

    JdbcDataTemplateImpl(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData entityClassMetaData) {
        this.dbExecutor = dbExecutor
        this.entitySQLMetaData = entitySQLMetaData
        this.entityClassMetaData = entityClassMetaData
    }

    @Override
    Optional<T> findById(Object connection, long id) {
        return connection.execute(entitySQLMetaData.getSelectByIdSql(id))
    }

    @Override
    List<T> findAll(Object connection) {
        return connection.execute(entitySQLMetaData.getSelectAllSql())
    }

    @Override
    long insert(Object connection, T object) {
        def map = object.properties
        map.remove(entityClassMetaData.idField.name)
        return connection.execute(entitySQLMetaData.getInsertSql(map))
    }

    @Override
    long update(Object connection, T object) {
        def map = object.properties
        map.remove(entityClassMetaData.idField.name)
        def id = object.properties.get(entityClassMetaData.idField.name) as long
        return connection.execute(entitySQLMetaData.getUpdateSql(map, id))
    }
}
