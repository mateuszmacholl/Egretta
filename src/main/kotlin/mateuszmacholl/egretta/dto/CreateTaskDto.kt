package mateuszmacholl.egretta.dto

import java.util.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class CreateTaskDto {
    @NotEmpty
    @NotNull
    val name: String? = null

    @NotEmpty
    @NotNull
    val author: String? = null

    @NotNull
    val date: Date? = null

    val content: String? = null

    @NotEmpty
    @NotNull
    val subject: String? = null

    @NotNull
    val type: String? = null
}