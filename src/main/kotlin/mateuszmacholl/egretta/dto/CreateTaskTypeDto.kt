package mateuszmacholl.egretta.dto

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class CreateTaskTypeDto {
    @NotEmpty
    @NotNull
    val name: String? = null
}