package mateuszmacholl.egretta.converter

import mateuszmacholl.egretta.dto.CreateTaskDto
import mateuszmacholl.egretta.model.task.Task
import mateuszmacholl.egretta.service.SubjectService
import mateuszmacholl.egretta.service.TaskTypeService
import mateuszmacholl.egretta.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TaskConverter : Converter<CreateTaskDto, Task> {
    @Autowired
    lateinit var userService: UserService
    @Autowired
    lateinit var subjectService: SubjectService
    @Autowired
    lateinit var taskTypeService: TaskTypeService

    override fun convert(from: CreateTaskDto): Task {
        val author = userService.findByUsername(from.author!!)
        val subject = subjectService.findByName(from.subject!!)
        val type = taskTypeService.findByName(from.type!!)
        if (author == null || subject == null || type == null) {
            throw IllegalArgumentException()
        }
        return Task(
                from.name,
                from.content,
                author,
                subject,
                from.date,
                type

        )
    }

    override fun convert(from: List<CreateTaskDto>): List<Task> {
        return from.map { p -> convert(p) }.toList()
    }
}