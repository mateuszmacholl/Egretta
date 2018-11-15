package mateuszmacholl.egretta.converter

import mateuszmacholl.egretta.dto.CreateUserDto
import mateuszmacholl.egretta.model.User
import org.springframework.stereotype.Service

@Service
class UserConverter: Converter<CreateUserDto, User> {
    override fun convert(from: CreateUserDto): User {
        return User(
                from.username,
                from.email,
                from.password
        )
    }

    override fun convert(from: List<CreateUserDto>): List<User> {
        return from.map { t-> convert(t) }.toList()
    }
}