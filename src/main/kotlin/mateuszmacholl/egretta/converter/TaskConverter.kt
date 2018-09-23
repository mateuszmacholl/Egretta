package mateuszmacholl.egretta.converter

import mateuszmacholl.egretta.dto.CreateTaskDto
import mateuszmacholl.egretta.model.Task
import mateuszmacholl.egretta.service.SubjectService
import mateuszmacholl.egretta.service.TaskTypeService
import mateuszmacholl.egretta.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TaskConverter {
    @Autowired
    lateinit var userService: UserService
    @Autowired
    lateinit var subjectService: SubjectService
    @Autowired
    lateinit var taskTypeService: TaskTypeService

    fun toEntity(createTaskDto: CreateTaskDto): Task {
        val author = userService.findByUsername(createTaskDto.author!!)
        val subject = subjectService.findByName(createTaskDto.subject!!)
        val type = taskTypeService.findByName(createTaskDto.type!!)
        if (author == null || subject == null || type == null) {
            throw IllegalArgumentException()
        }
        return Task(
                null,
                createTaskDto.name,
                createTaskDto.content,
                author,
                subject,
                createTaskDto.date,
                type

        )
    }

    fun toEntity(createProductDtos: List<CreateTaskDto>): List<Task> {
        return createProductDtos.map { p -> toEntity(p) }.toList()
    }
}