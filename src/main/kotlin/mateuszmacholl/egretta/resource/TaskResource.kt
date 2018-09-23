package mateuszmacholl.egretta.resource

import mateuszmacholl.egretta.controller.SubjectController
import mateuszmacholl.egretta.controller.TaskController
import mateuszmacholl.egretta.controller.TaskTypeController
import mateuszmacholl.egretta.controller.UserController
import mateuszmacholl.egretta.model.Task
import org.springframework.hateoas.ResourceSupport
import org.springframework.hateoas.mvc.ControllerLinkBuilder
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn

class TaskResource() : ResourceSupport() {
   // var entityLinks: EntityLinks = BeanUtil.getBean(EntityLinks::class.java)
    var task: Task? = null

    constructor(task: Task): this() {
        this.task = task
        val id = task.id
       // this.add(entityLinks.linkToSingleResource(User::class.java, id))
        add(linkTo(TaskController::class.java).slash(id).withSelfRel())
        add(linkTo(methodOn(UserController::class.java)
                .getById(task.author!!.id!!)).withRel("author").withTitle(task.author.username))
        add(linkTo(ControllerLinkBuilder
                .methodOn(TaskTypeController::class.java).getById(task.type!!.id!!)).withRel("type").withTitle(task.type.name))
        add(linkTo(ControllerLinkBuilder
                .methodOn(SubjectController::class.java).getById(task.subject!!.id!!)).withRel("subject").withTitle(task.subject.name))

    }
}