package mateuszmacholl.egretta.controller

import mateuszmacholl.egretta.resource.UserResource
import mateuszmacholl.egretta.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.hateoas.Resources
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Specification

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.ANY)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest extends Specification {
    @Autowired
    UserService userService
    @Autowired
    private TestRestTemplate restTemplate

    def "get all users"() {
        when:
        def response = restTemplate.getForEntity('/users', Resources.class)

        then:
        HttpStatus.OK == response.statusCode
    }

    def "get user by username"() {
        given:
        def username = "d_freshly_user"

        when:
        def response = restTemplate.getForEntity('/users?username=' + username, UserResource.class)

        then:
        HttpStatus.OK == response.statusCode
        
        username == response.body.user.username
    }

    def "get user by id"() {
        given:
        def id = 1001
        when:
        def response = restTemplate.getForEntity('/users/' + id, UserResource.class)

        then:
        HttpStatus.OK == response.statusCode
    }

    def "get user tasks by id"() {
        given:
        def id = 1001
        when:
        def response = restTemplate.getForEntity('/users/' + id + '/tasks', Resources.class)

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
