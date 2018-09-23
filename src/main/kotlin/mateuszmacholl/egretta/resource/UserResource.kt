package mateuszmacholl.egretta.resource

import mateuszmacholl.egretta.controller.TaskController
import mateuszmacholl.egretta.controller.UserController
import mateuszmacholl.egretta.model.User
import org.springframework.hateoas.ResourceSupport
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn

class UserResource() : ResourceSupport() {
    var user: User? = null

    constructor(user: User): this() {
        this.user = user
        val id = user.id
        add(linkTo(methodOn(UserController::class.java).getById(id!!)).withSelfRel())
        add(linkTo(methodOn(UserController::class.java).getUserTasks(id)).withRel("tasks"))
        this.user!!.tasks.map {
            t -> add(linkTo(methodOn(TaskController::class.java).getById(t.id!!)).withRel("tasks"))
        }
    }
}