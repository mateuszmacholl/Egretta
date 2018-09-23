package mateuszmacholl.egretta.controller

import mateuszmacholl.egretta.converter.UserConverter
import mateuszmacholl.egretta.dto.CreateUserDto
import mateuszmacholl.egretta.model.specification.UserSpec
import mateuszmacholl.egretta.resource.TaskResource
import mateuszmacholl.egretta.resource.UserResource
import mateuszmacholl.egretta.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.hateoas.Resources
import org.springframework.hateoas.mvc.ControllerLinkBuilder
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@Validated
@RequestMapping(value = ["/users"], produces = ["application/hal+json"])
class UserController {
    @Autowired
    lateinit var userService: UserService
    @Autowired
    lateinit var userConverter: UserConverter


    @RequestMapping(value = [""], method = [RequestMethod.GET])
    fun getAllBy(userSpec: UserSpec, pageable: Pageable): ResponseEntity<*> {
        val users = userService.findAll(userSpec, pageable)
        val userResources = users.map { u -> UserResource(u) }.toList()

        val link = ControllerLinkBuilder.linkTo(this::class.java).withSelfRel()
        val result = Resources<UserResource>(userResources, link)
        return ResponseEntity(result, HttpStatus.OK)
    }

    @RequestMapping(value = ["/{id}"], method = [RequestMethod.GET])
    fun getById(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val user = userService.findById(id)
        return if (!user.isPresent) {
            ResponseEntity("user not found", HttpStatus.NOT_FOUND)
        } else {
            val userResource = UserResource(user.get())
            ResponseEntity(userResource, HttpStatus.OK)
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
            val taskResources = user.get().tasks.map { t -> TaskResource(t) }.toList()

            val link = ControllerLinkBuilder.linkTo(this::class.java).withSelfRel()
            val result = Resources<TaskResource>(taskResources, link)
            ResponseEntity(result, HttpStatus.OK)
        }
    }

}