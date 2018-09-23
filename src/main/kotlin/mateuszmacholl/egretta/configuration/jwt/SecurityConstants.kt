package mateuszmacholl.egretta.configuration.jwt

object SecurityConstants {
    val SECRET = "MySecretKey"
    val EXPIRATION_TIME: Long = 864000000 // 10 days
    val TOKEN_PREFIX = "Bearer "
    val HEADER_STRING = "Authorization"
}
