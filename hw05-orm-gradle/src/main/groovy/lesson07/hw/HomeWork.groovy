package lesson07.hw

import groovy.json.JsonSlurper
import lesson07.hw.datasource.DriverManagerDataSource
import lesson07.hw.mapper.impl.EntityClassMetaDataImpl
import lesson07.hw.mapper.impl.EntitySQLMetaDataImpl
import lesson07.hw.model.Client
import lesson07.hw.model.Manager
import lesson07.hw.repository.DbExecutorImpl
import lesson07.hw.repository.impl.JdbcDataTemplateImpl
import lesson07.hw.service.DbServiceClientImpl
import lesson07.hw.service.DbServiceManagerImpl
import lesson07.hw.sessionmanager.TransactionRunnerJdbc
import org.flywaydb.core.Flyway
import org.slf4j.Logger

import javax.sql.DataSource

import static org.slf4j.LoggerFactory.getLogger

//final URL = "jdbc:postgresql://localhost:5432/OtusDbGroovy"
//final USER = "postgres"
//final PASSWORD = "postgres"

//load peroperties with Input stream
Properties properties = new Properties()
File propertiesFile = new File('test.properties')
propertiesFile.withInputStream {
    properties.load(it)
}

final URL = properties.url
final USER = properties.user
final PASSWORD = properties.password

//load peroperties with Slurper(need correct file JSON format)
//def fileName = 'dbConnection.properties'
//def file = getClass().getResource("/${fileName}")
//def slurper = new JsonSlurper()
//def props = slurper.parseText(file.text)
//
//final URL = props.url
//final USER = props.user
//final PASSWORD = props.password

final Logger log = getLogger(HomeWork.class)

// Общая часть
def dataSource = new DriverManagerDataSource(URL, USER, PASSWORD)
flywayMigrations(dataSource)
def transactionRunner = new TransactionRunnerJdbc(dataSource)
def dbExecutor = new DbExecutorImpl()

// Работа с клиентом
def entityClassMetaDataClient = new EntityClassMetaDataImpl<>(Client.class)
def entitySQLMetaDataClient = new EntitySQLMetaDataImpl<>(entityClassMetaDataClient)
def dataTemplateClient = new JdbcDataTemplateImpl<>(dbExecutor, entitySQLMetaDataClient, entityClassMetaDataClient) //реализация DataTemplate, универсальная

// Код дальше должен остаться
def dbServiceClient = new DbServiceClientImpl(transactionRunner, dataTemplateClient)
def clientAfterSave = dbServiceClient.saveClient(new Client("dbServiceFirst"))
log.info(">>>after save::{}", clientAfterSave)
def clientSecond = dbServiceClient.saveClient(new Client("dbServiceSecond"))
def clientSecondSelected = dbServiceClient.getClient(clientSecond.getId())
        .orElseThrow(() -> new RuntimeException("Client not found, id:" + clientSecond.getId()))
log.info("clientSecondSelected:{}", clientSecondSelected)
log.info(">>>All clients::{}", dbServiceClient.findAll())
def clientForUpdate = clientSecondSelected
clientForUpdate.setName("New name for client")
def clientAfterUpdate = dbServiceClient.saveClient(clientForUpdate)

log.info(">>>Last client after update::{}", clientAfterUpdate)
// Сделайте тоже самое с классом Manager (для него надо сделать свою таблицу)

def entityClassMetaDataManager = new EntityClassMetaDataImpl<>(Manager.class)
def entitySQLMetaDataManager = new EntitySQLMetaDataImpl<>(entityClassMetaDataManager)
def dataTemplateManager = new JdbcDataTemplateImpl<>(dbExecutor, entitySQLMetaDataManager, entityClassMetaDataManager)

def dbServiceManager = new DbServiceManagerImpl(transactionRunner, dataTemplateManager)
def managerAfterSave = dbServiceManager.saveManager(new Manager("ManagerFirst"))
log.info(">>>after save::{}", managerAfterSave)

def managerSecond = dbServiceManager.saveManager(new Manager("ManagerSecond"))
def managerSecondSelected = dbServiceManager.getManager(managerSecond.getNo())
        .orElseThrow(() -> new RuntimeException("Manager not found, id:" + managerSecond.getNo()))
log.info("managerSecondSelected:{}", managerSecondSelected)
log.info(">>>All managers::{}", dbServiceManager.findAll())

def managerForUpdate = managerSecondSelected
managerForUpdate.setLabel("New Label for manager")
managerForUpdate.setParam1("New param for manager")
def managerAfterUpdate = dbServiceManager.saveManager(managerForUpdate)
log.info(">>>Last manager after update::{}", managerAfterUpdate)

private static void flywayMigrations(DataSource dataSource) {
    def logger = getLogger(HomeWork.class)
    logger.info("db migration started...")
    def flyway = Flyway.configure()
            .dataSource(dataSource)
            .locations("classpath:/db/migration")
            .load()
    flyway.migrate()
    logger.info("db migration finished.")
    logger.info("***")
}
