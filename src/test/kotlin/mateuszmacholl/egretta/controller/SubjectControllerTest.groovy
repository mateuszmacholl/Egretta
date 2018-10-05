package mateuszmacholl.egretta.controller

import mateuszmacholl.egretta.model.Subject
import mateuszmacholl.egretta.model.task.Task
import mateuszmacholl.egretta.service.SubjectService
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
class SubjectControllerTest extends Specification {
    @Autowired
    private SubjectService subjectService
    @Autowired
    private TestRestTemplate restTemplate

    def "get all subjects"() {
        when:
        def response = restTemplate.getForEntity('/subjects', String.class)

        then:
        HttpStatus.OK == response.statusCode
    }

    def "get subject by id"() {
        given:
        def id = 1001
        when:
        def response = restTemplate.getForEntity('/subjects/' + id, Subject.class)

        then:
        HttpStatus.OK == response.statusCode
    }

    def "delete subject by id"() {
        given:
        def id = 1000
        when:
        def response = restTemplate.exchange('/subjects/' + id, HttpMethod.DELETE, null, String.class)

        then:
        HttpStatus.NO_CONTENT == response.statusCode

        def subject = subjectService.findById(id)
        subject == Optional.empty()
    }

    def "add subject"() {
        given:
        def name = "biologia"
        def body = [
                name   : name
        ]
        when:
        def response = restTemplate.postForEntity('/subjects', body, String.class)

        then:
        HttpStatus.CREATED == response.statusCode

        def subjects = SubjectService.findAll() as List<Subject>
        subjects.stream().filter { t ->
            (
                    t.name == name
            )
        } != Optional.empty()
    }

    def "get subject tasks by id"() {
        given:
        def id = 1000
        when:
        def response = restTemplate.getForEntity('/subjects/' + id + '/tasks', Task[].class)

        then:
        HttpStatus.OK == response.statusCode
    }
}
