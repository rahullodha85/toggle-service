package constants

object Constants {
  val TIMEOUT_MSG = "request timed out"
  val ZULU_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ"
  val ERROR_MESSAGE = "error"

  val MONGO_CONFIG = "mongo"
  val MONGO_URI = "mongoUri"
  val DB_NAME = "dbName"
  val DB_COLLECTION = "dbCollection"
  val HISTORY_COLLECTION = "historyCollection"
  val USAGE_COLLECTION = "usageCollection"
  val COUNTS_COLLECTION = "countsCollection"
  val CONNECTIONS_PER_HOST = "connectionsPerHost"
  val CONNECT_TIMEOUT = "connectTimeout"
  val MAX_WAIT_TIME = "maxWaitTime"
  val THREADS_ALLOWED_TO_BLOCK_FOR_CONNECTION_MULTIPLIER = "threadsAllowedToBlockForConnectionMultiplier"
  val READ_PREFERENCE = "readPreference"

  val JSESSIONID = "JSESSIONID"
  val USERNAME = "UserName"

  // extras

  val TOGGLE_NAME_REGEX = "[-a-zA-Z0-9_\\s.]+".r

  val TOGGLE_API_DB = "toggle_service"
  val REQUEST = "request"
  val RESPONSE = "response"
  val ERRORS = "errors"
  val URL = "url"
  val HELP = "help"
  val SERVER_RECEIVED_TIME = "server_received_time"
  val MESSAGES = "messages"
  val MESSAGE = "message"
  val DATA = "data"
  val ERROR = "error"
  val CONTENT_TYPE = "application/json"
  val CONTENT_TYPE_KEY = "Content-type"
  val ITEMS = "items"
  val RESULT = "result"
  val RESULTS = "results"
  val RESPONSE_STATUS = "status"
  val STATUS_COMPLETE = "complete"
  val STATUS_ERROR = "error"
  val STATUS_EMPTY = "empty"
  val PLAY = "play"

  val _ID = "_id"
  val SEQ = "seq"

  //TOGGLE constants
  val TOGGLE_COLLECTION = "toggle"
  val TOGGLE_HISTORY_COLLECTION = "toggle_history"
  val TOGGLE_NAME = "toggle_name"
  val TOGGLE_STATE = "toggle_state"
  val TIMESTAMP_CREATED = "timestamp_created"
  val TIMESTAMP_STATUS_CHANGE = "timestamp_status_change"
  val CREATED_TIMESTAMP = "created_timestamp"
  val MODIFIED_TIMESTAMP = "modified_timestamp"
  val DESCRIPTION = "description"
  val TOGGLE_TYPE = "toggle_type"
  val MODIFIED_BY = "modified_by"
  val ACTION = "action"
  val HISTORY_DETAILS = "details"
  val USERS = "users"
  val USER = "user"
  val TOGGLES = "toggles"

  //Error Messages
  val ROUTE_NOT_FOUND = "Sorry, invalid route."
  val NO_ITEMS = "No items to retrieve."
  val INVALID_JSON_PAYLOAD = "Expecting JSON Data, invalid POST payload."

  val MONGO_DB_CONFIG_KEY = "mongoDBToggle"

  val HOST = "host"
  val PORT = "port"

  //TOGGLE constants
  val TOGGLES_COLLECTION = "toggles"
  val ENABLED = "enabled"
  val TOGGLE_REGISTRATION_SUCCESS = true
  val TOGGLE_REGISTRATION_FAILURE = false

  //Authentication
  val ROLE_USER = "ROLE_USER"

  val HBC_ENV = "hbc.env"
  val HBC_BANNER = "hbc.banner"
  val PROD_ENV = "Prod"

  val CONFIRM_KEY = "confirm"
  val ENVS_DISALLOWING_CREATE_DELETE = "envs_disallowing_create_delete"

  val EXPIRATION_WINDOW_DAYS = 90
}
