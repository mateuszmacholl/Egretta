package mateuszmacholl.egretta.dto

import mateuszmacholl.egretta.utils.TaskState
import javax.validation.constraints.NotNull

class UpdateStateTaskDto(
        @NotNull
        val state: TaskState
)