package mateuszmacholl.egretta.controller

import mateuszmacholl.egretta.model.Task
import mateuszmacholl.egretta.service.TaskService
import mateuszmacholl.egretta.utils.TaskState
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import java.text.SimpleDateFormat

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles(value = ["test"])
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskControllerTest extends Specification {
    @Autowired
    private TaskService taskService
    @Autowired
    private TestRestTemplate restTemplate

    def "get all tasks"() {
        when:
        def response = restTemplate.getForEntity('/tasks', String.class)

        then:
        HttpStatus.OK == response.statusCode
    }

    def "get task by id"() {
        given:
        def id = 1001
        when:
        def response = restTemplate.getForEntity('/tasks/' + id, Task.class)

        then:
        HttpStatus.OK == response.statusCode
    }

    def "delete task by id"() {
        given:
        def id = 1000
        when:
        def response = restTemplate.exchange('/tasks/' + id, HttpMethod.DELETE, null, String.class)

        then:
        HttpStatus.NO_CONTENT == response.statusCode

        def task = taskService.findById(id)
        task == Optional.empty()
    }

    def "add task"() {
        given:
        def subject = "polski"
        def author = "d_enabled_user"
        def date = '2018-10-01'
        def name = 'sprawdzian z dat'
        def type = 'test'
        def body = [
                subject: subject,
                author : author,
                date   : date,
                name   : name,
                type: type
        ]
        when:
        def response = restTemplate.postForEntity('/tasks', body, String.class)

        then:
        HttpStatus.CREATED == response.statusCode

        def formatter = new SimpleDateFormat("dd-MMM-yyyy")

        def tasks = taskService.findAll() as List<Task>
        tasks.stream().filter { t ->
            (
                    t.name == name
                            && t.date== formatter.parse(date)
                            && t.author.username == author
                            && t.subject.name == subject
            )
        } != Optional.empty()
    }

    def "update state task"() {
        given:
        def id = 1000
        def state = TaskState.DONE
        def body = [
                state: state
        ]
        when:
        def response = restTemplate.exchange('/tasks/' + id + '/state', HttpMethod.PUT, new HttpEntity<Object>(body), String.class)

        then:
        HttpStatus.NO_CONTENT == response.statusCode

        def task = taskService.findById(id)
        task.get().state == state
    }
}
