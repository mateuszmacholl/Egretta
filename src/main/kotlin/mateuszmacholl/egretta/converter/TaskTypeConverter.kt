package mateuszmacholl.egretta.converter

import mateuszmacholl.egretta.dto.CreateTaskTypeDto
import mateuszmacholl.egretta.model.TaskType
import org.springframework.stereotype.Service

@Service
class TaskTypeConverter: Converter<CreateTaskTypeDto, TaskType> {
    override fun convert(from: CreateTaskTypeDto): TaskType {
        return TaskType(
                from.name
        )
    }

    override fun convert(from: List<CreateTaskTypeDto>): List<TaskType> {
        return from.map { t-> convert(t) }.toList()
    }
}