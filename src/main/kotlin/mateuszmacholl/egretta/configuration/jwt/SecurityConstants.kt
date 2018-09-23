package mateuszmacholl.egretta.configuration.jwt

object SecurityConstants {
    const val SECRET = "SeceretSecretSecretKey"
    const val EXPIRATION_TIME: Long = 864000000 // 10 days
    const val TOKEN_PREFIX = "Bearer "
    const val HEADER_STRING = "Authorization"
}
