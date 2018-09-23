package mateuszmacholl.egretta.controller

import mateuszmacholl.egretta.resource.TaskTypeResource
import mateuszmacholl.egretta.service.TaskTypeService
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
class TaskTypeControllerTest extends Specification {
    @Autowired
    private TaskTypeService taskTypeService
    @Autowired
    private TestRestTemplate restTemplate

    def "get all task types"() {
        when:
        def response = restTemplate.getForEntity('/task-types', Resources.class)

        then:
        HttpStatus.OK == response.statusCode
    }

    def "get task type by id"() {
        given:
        def id = 1001
        when:
        def response = restTemplate.getForEntity('/task-types/' + id, TaskTypeResource.class)

        then:
        HttpStatus.OK == response.statusCode
    }

    def "delete task type by id"() {
        given:
        def id = 1000
        when:
        def response = restTemplate.exchange('/task-types/' + id, HttpMethod.DELETE, null, String.class)

        then:
        HttpStatus.NO_CONTENT == response.statusCode

        def taskType = taskTypeService.findById(id)
        taskType == Optional.empty()
    }

    def "add task type"() {
        given:
        def name = "praca domowa"
        def body = [
                name: name
        ]
        when:
        def response = restTemplate.postForEntity('/task-types', body, String.class)

        then:
        HttpStatus.CREATED == response.statusCode

        def taskTypes = taskTypeService.findAll() asList()
        taskTypes.stream().filter { t ->
            (
                    t.name == name
            )
        } != Optional.empty()
    }

    def "get task type tasks by id"() {
        given:
        def id = 1001
        when:
        def response = restTemplate.getForEntity('/task-types/' + id + '/tasks', Resources.class)

        then:
        HttpStatus.OK == response.statusCode
    }
}
