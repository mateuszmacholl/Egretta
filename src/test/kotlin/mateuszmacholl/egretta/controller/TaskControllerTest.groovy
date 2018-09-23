package mateuszmacholl.egretta.controller

import mateuszmacholl.egretta.resource.TaskResource
import mateuszmacholl.egretta.service.TaskService
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

import java.text.SimpleDateFormat

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.ANY)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskControllerTest extends Specification {
    @Autowired
    private TaskService taskService
    @Autowired
    private TestRestTemplate restTemplate

    def "get all tasks"() {
        when:
        def response = restTemplate.getForEntity('/tasks', Resources.class)

        then:
        HttpStatus.OK == response.statusCode
    }

    def "get task by id"() {
        given:
        def id = 1001
        when:
        def response = restTemplate.getForEntity('/tasks/' + id, TaskResource.class)

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

        def tasks = taskService.findAll() asList()
        tasks.stream().filter { t ->
            (
                    t.name == name
                            && t.date== formatter.parse(date)
                            && t.author.username == author
                            && t.subject.name == subject
            )
        } != Optional.empty()
    }
}
