package mateuszmacholl.egretta.converter

import mateuszmacholl.egretta.dto.CreateUserDto
import mateuszmacholl.egretta.model.User
import org.springframework.stereotype.Service

@Service
class UserConverter {
    fun toEntity(createUserDto: CreateUserDto): User {
        return User(
                null,
                createUserDto.username,
                createUserDto.email,
                createUserDto.password
        )
    }

    fun toEntity(createUserDtos: List<CreateUserDto>): List<User> {
        return createUserDtos.map { t-> toEntity(t) }.toList()
    }
}