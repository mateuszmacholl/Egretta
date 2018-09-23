package mateuszmacholl.egretta.resource

import mateuszmacholl.egretta.controller.SubjectController
import mateuszmacholl.egretta.model.Subject
import org.springframework.hateoas.ResourceSupport
import org.springframework.hateoas.mvc.ControllerLinkBuilder
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo

class SubjectResource() : ResourceSupport() {
    var subject: Subject? = null

    constructor(subject: Subject) : this() {
        this.subject = subject
        val id = subject.id
        add(linkTo(SubjectController::class.java).slash(id).withSelfRel())
        add(linkTo(ControllerLinkBuilder
                .methodOn(SubjectController::class.java).getSubjectTasks(subject.id!!)).withRel("tasks"))
    }
}