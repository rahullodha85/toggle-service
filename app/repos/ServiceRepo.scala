package repos

import com.mongodb.casbah.{MongoClient, MongoClientURI, MongoCollection, MongoDB}
import com.mongodb.{MongoClientOptions, ReadPreference}
import com.typesafe.config.Config
import constants.Constants._
import helpers.ConfigurationProvider
import play.api.Configuration

object ServiceRepo extends ServiceRepo(ConfigurationProvider.getConfiguration)

class ServiceRepo(configuration: Configuration) {
  var mongoConn: MongoClient = _
  var mongoDB: MongoDB = _
  var mongoCollection: MongoCollection = _
  var historyCollection: MongoCollection = _
  var usageCollection: MongoCollection = _
  var countsCollection: MongoCollection = _

  def createServiceRepoClient = {
    val mongoConfig = configuration.getConfig(MONGO_CONFIG).get
    mongoConn = getMongoClient(mongoConfig)
    mongoDB = mongoConn.apply(mongoConfig.getString(DB_NAME).get)

    mongoCollection = mongoDB.apply(mongoConfig.getString(DB_COLLECTION).get)
    historyCollection = mongoDB.apply(mongoConfig.getString(HISTORY_COLLECTION).get)
    usageCollection = mongoDB.apply(mongoConfig.getString(USAGE_COLLECTION).get)
    countsCollection = mongoDB.apply(mongoConfig.getString(COUNTS_COLLECTION).get)
  }

  def destroyServiceRepoClient = {
    mongoConn.close
    mongoConn = null
    mongoDB = null
    mongoConn = null
    mongoCollection = null
  }

  protected def getMongoClient(mongoConfig: Configuration): MongoClient = {
    val uri = s"mongodb://${mongoConfig.getString("mongoUser").get}:${mongoConfig.getString("mongoPassword").get}@${mongoConfig.getString("mongoHost").get}"
    val mongoClientURI = MongoClientURI(uri)
    MongoClient(mongoClientURI)
  }

  def getMongoOptions(mongoConfig: Config): MongoClientOptions = {
    MongoClientOptions.builder
      .connectionsPerHost(mongoConfig.getInt(CONNECTIONS_PER_HOST))
      .threadsAllowedToBlockForConnectionMultiplier(mongoConfig.getInt(THREADS_ALLOWED_TO_BLOCK_FOR_CONNECTION_MULTIPLIER))
      .connectTimeout(mongoConfig.getInt(CONNECT_TIMEOUT))
      .maxWaitTime(mongoConfig.getInt(MAX_WAIT_TIME))
      .readPreference(ReadPreference.valueOf(mongoConfig.getString(READ_PREFERENCE))).build
  }
}
