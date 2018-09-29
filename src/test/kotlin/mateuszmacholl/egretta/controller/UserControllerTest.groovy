package mateuszmacholl.egretta.controller

import mateuszmacholl.egretta.model.Task
import mateuszmacholl.egretta.model.User
import mateuszmacholl.egretta.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles(value = ["test"])
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest extends Specification {
    @Autowired
    UserService userService
    @Autowired
    private TestRestTemplate restTemplate

    def "get all users"() {
        when:
        def response = restTemplate.getForEntity('/users', String.class)

        then:
        HttpStatus.OK == response.statusCode
    }

    def "get user by id"() {
        given:
        def id = 1001
        when:
        def response = restTemplate.getForEntity('/users/' + id, User.class)

        then:
        HttpStatus.OK == response.statusCode
    }

    def "get user tasks by id"() {
        given:
        def id = 1001
        when:
        def response = restTemplate.getForEntity('/users/' + id + '/tasks', Task[].class)

        then:
        HttpStatus.OK == response.statusCode
    }

    def "delete user by id"() {
        given:
        def id = 1000
        when:
        def response = restTemplate.exchange('/users/' + id, HttpMethod.DELETE, null, String.class)

        then:
        HttpStatus.NO_CONTENT == response.statusCode

        def task = userService.findById(id)
        task == Optional.empty()
    }

}
