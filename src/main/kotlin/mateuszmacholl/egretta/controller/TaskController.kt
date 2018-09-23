package mateuszmacholl.egretta.controller

import mateuszmacholl.egretta.converter.TaskConverter
import mateuszmacholl.egretta.dto.CreateTaskDto
import mateuszmacholl.egretta.model.specification.TaskSpec
import mateuszmacholl.egretta.resource.TaskResource
import mateuszmacholl.egretta.service.TaskService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.hateoas.Resources
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@Validated
@RequestMapping(value = ["/tasks"], produces = ["application/hal+json"])
class TaskController {
    @Autowired
    lateinit var taskService: TaskService
    @Autowired
    lateinit var taskConverter: TaskConverter

    @RequestMapping(value = [""], method = [RequestMethod.GET])
    fun getAll(taskSpec: TaskSpec, pageable: Pageable): ResponseEntity<*> {
        val tasks = taskService.findAll(taskSpec, pageable)
        val taskResources = tasks.map { p -> TaskResource(p) }.toList()

        val link = linkTo(this::class.java).withSelfRel()
        val result = Resources<TaskResource>(taskResources, link)
        return ResponseEntity(result, HttpStatus.OK)
    }

    @RequestMapping(value = ["/{id}"], method = [RequestMethod.GET])
    fun getById(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val task = taskService.findById(id)
        return if (!task.isPresent) {
            ResponseEntity("task not found", HttpStatus.NOT_FOUND)
        } else {
            val taskResource = TaskResource(task.get())
            ResponseEntity(taskResource, HttpStatus.OK)
        }
    }

    @RequestMapping(value = [""], method = [RequestMethod.POST])
    fun add(@RequestBody @Validated createTaskDto: CreateTaskDto): ResponseEntity<*> {
        val task = taskConverter.toEntity(createTaskDto)
        taskService.add(task)
        return ResponseEntity<Any>(HttpStatus.CREATED)
    }

    @RequestMapping(value = ["/{id}"], method = [RequestMethod.DELETE])
    fun delete(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val task = taskService.findById(id)
        return if (!task.isPresent) {
            ResponseEntity("task not found", HttpStatus.NOT_FOUND)
        } else {
            taskService.delete(task.get())
            return ResponseEntity<Any>(HttpStatus.NO_CONTENT)
        }
    }

}