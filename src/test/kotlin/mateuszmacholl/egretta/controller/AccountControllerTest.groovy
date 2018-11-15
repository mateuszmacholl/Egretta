package mateuszmacholl.egretta.controller

import mateuszmacholl.egretta.model.User
import mateuszmacholl.egretta.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import spock.lang.Specification

@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.ANY)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountControllerTest extends Specification {
    @Autowired
    UserService userService
    @Autowired
    private TestRestTemplate restTemplate

    def "registration successful"() {
        given:
        def username = "username"
        def email = "email@gmail.com"
        def password = "password"
        def clientVerifyAccountUrl = "clientVerifyAccountUrl"

        Map body = [
                email                 : email,
                username              : username,
                password              : password,
                clientVerifyAccountUrl: clientVerifyAccountUrl
        ]

        when:
        def response = restTemplate.postForEntity('/users', body, String.class)

        then:
        HttpStatus.CREATED == response.statusCode


        User user = userService.findByUsername("username")
        user != null //created user
        username == user.username
        email == user.email
        password != user.password // different password (encoding)
        user.enabled // user is enabled
    }

    def "login successful"() {
        given:
        def username = 'username'
        def password = 'password'
        Map body = [
                username: username,
                password: password
        ]
        when:
        def response = restTemplate.postForEntity('/users/login', body, String.class)

        then:
        HttpStatus.OK == response.statusCode
    }


}