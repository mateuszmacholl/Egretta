package mateuszmacholl.egretta.controller

import mateuszmacholl.egretta.converter.UserConverter
import mateuszmacholl.egretta.dto.CreateUserDto
import mateuszmacholl.egretta.model.specification.UserSpec
import mateuszmacholl.egretta.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@Validated
@RequestMapping(value = ["/users"])
class UserController {
    @Autowired
    lateinit var userService: UserService
    @Autowired
    lateinit var userConverter: UserConverter


    @RequestMapping(value = [""], method = [RequestMethod.GET])
    fun getAllBy(userSpec: UserSpec, pageable: Pageable): ResponseEntity<*> {
        val users = userService.findAll(userSpec, pageable)
        return ResponseEntity(users, HttpStatus.OK)
    }

    @RequestMapping(value = ["/{id}"])
    fun getById(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val user = userService.findById(id)
        return if (!user.isPresent) {
            ResponseEntity("user not found", HttpStatus.NOT_FOUND)
        } else {
            ResponseEntity(user, HttpStatus.OK)
        }
    }

    @RequestMapping(value = [""], method = [RequestMethod.POST])
    fun add(@RequestBody @Validated createUserDto: CreateUserDto): ResponseEntity<*> {

        val user = userConverter.toEntity(createUserDto)
        userService.add(user)

        return ResponseEntity<Any>(HttpStatus.CREATED)
    }

    @RequestMapping(value = ["/{id}"], method = [RequestMethod.DELETE])
    fun delete(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val user = userService.findById(id)
        return if (!user.isPresent) {
            ResponseEntity("user not found", HttpStatus.NOT_FOUND)
        } else {
            userService.delete(user.get())
            return ResponseEntity<Any>(HttpStatus.NO_CONTENT)
        }
    }

    @RequestMapping(value = ["/{id}/tasks"], method = [RequestMethod.GET])
    fun getUserTasks(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val user = userService.findById(id)
        return if (!user.isPresent) {
            ResponseEntity("user not found", HttpStatus.NOT_FOUND)
        } else {
            ResponseEntity(user.get().tasks, HttpStatus.OK)
        }
    }

}