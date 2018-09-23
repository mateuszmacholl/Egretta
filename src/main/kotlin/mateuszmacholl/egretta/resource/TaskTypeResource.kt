package mateuszmacholl.egretta.resource

import mateuszmacholl.egretta.controller.TaskTypeController
import mateuszmacholl.egretta.model.TaskType
import org.springframework.hateoas.ResourceSupport
import org.springframework.hateoas.mvc.ControllerLinkBuilder

class TaskTypeResource() : ResourceSupport() {
    var taskType: TaskType? = null

    constructor(taskType: TaskType) : this(){
        this.taskType = taskType
        val id = taskType.id
        add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(TaskTypeController::class.java).getById(id!!)).withSelfRel())
        add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(TaskTypeController::class.java).getTaskTypeTasks(taskType.id)).withRel("tasks"))
    }
}