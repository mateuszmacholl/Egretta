package mateuszmacholl.egretta.dto

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class CreateUserDto {
    @NotEmpty
    @NotNull
    val username: String? = null

    @NotEmpty
    @NotNull
    val email: String? = null

    @NotEmpty
    @NotNull
    val password: String? = null
}
