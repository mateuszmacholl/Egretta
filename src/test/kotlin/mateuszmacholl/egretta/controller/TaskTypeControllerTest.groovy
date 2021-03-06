package mateuszmacholl.egretta.controller

import mateuszmacholl.egretta.model.task.Task
import mateuszmacholl.egretta.model.TaskType
import mateuszmacholl.egretta.service.TaskTypeService
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
class TaskTypeControllerTest extends Specification {
    @Autowired
    private TaskTypeService taskTypeService
    @Autowired
    private TestRestTemplate restTemplate

    def "get all task types"() {
        when:
        def response = restTemplate.getForEntity('/task-types', String.class)

        then:
        HttpStatus.OK == response.statusCode
    }

    def "get task type by id"() {
        given:
        def id = 1001
        when:
        def response = restTemplate.getForEntity('/task-types/' + id, TaskType.class)

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

        def taskTypes = taskTypeService.findAll() as List<TaskType>
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
        def response = restTemplate.getForEntity('/task-types/' + id + '/tasks', Task[].class)

        then:
        HttpStatus.OK == response.statusCode
    }
}
