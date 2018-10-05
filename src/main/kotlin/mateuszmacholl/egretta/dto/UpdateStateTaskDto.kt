package mateuszmacholl.egretta.dto

import mateuszmacholl.egretta.model.task.TaskState
import javax.validation.constraints.NotNull

class UpdateStateTaskDto(
        @NotNull
        val state: TaskState
)