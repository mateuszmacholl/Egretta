package mateuszmacholl.egretta.converter

import mateuszmacholl.egretta.dto.CreateTaskTypeDto
import mateuszmacholl.egretta.model.TaskType
import org.springframework.stereotype.Service

@Service
class TaskTypeConverter {
    fun toEntity(createTaskTypeDto: CreateTaskTypeDto): TaskType {
        return TaskType(
                null,
                createTaskTypeDto.name
        )
    }

    fun toEntity(createSubjectDtos: List<CreateTaskTypeDto>): List<TaskType> {
        return createSubjectDtos.map { t-> toEntity(t) }.toList()
    }
}