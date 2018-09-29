package mateuszmacholl.egretta.controller

import mateuszmacholl.egretta.converter.TaskTypeConverter
import mateuszmacholl.egretta.dto.CreateTaskTypeDto
import mateuszmacholl.egretta.model.specification.TaskTypeSpec
import mateuszmacholl.egretta.service.TaskTypeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@Validated
@RequestMapping(value = ["/task-types"], produces = ["application/hal+json"])
class TaskTypeController {
    @Autowired
    lateinit var taskTypeService: TaskTypeService
    @Autowired
    lateinit var taskTypeConverter: TaskTypeConverter

    @RequestMapping(value = [""], method = [RequestMethod.GET])
    fun getAll(taskTypeSpec: TaskTypeSpec, pageable: Pageable): ResponseEntity<*> {
        val taskTypes = taskTypeService.findAll(taskTypeSpec, pageable)
        return ResponseEntity(taskTypes, HttpStatus.OK)
    }

    @RequestMapping(value = ["/{id}"])
    fun getById(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val taskType = taskTypeService.findById(id)
        return if (!taskType.isPresent) {
            ResponseEntity("task type not found", HttpStatus.NOT_FOUND)
        } else {
            ResponseEntity(taskType, HttpStatus.OK)
        }
    }

    @RequestMapping(value = [""], method = [RequestMethod.POST])
    fun add(@RequestBody @Validated createTaskTypeDto: CreateTaskTypeDto): ResponseEntity<*> {
        val taskType = taskTypeConverter.toEntity(createTaskTypeDto)
        taskTypeService.add(taskType)
        return ResponseEntity<Any>(HttpStatus.CREATED)
    }

    @RequestMapping(value = ["/{id}"], method = [RequestMethod.DELETE])
    fun delete(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val taskType = taskTypeService.findById(id)
        return if (!taskType.isPresent) {
            ResponseEntity("task type not found", HttpStatus.NOT_FOUND)
        } else {
            taskTypeService.delete(taskType.get())
            return ResponseEntity<Any>(HttpStatus.NO_CONTENT)
        }
    }

    @RequestMapping(value = ["/{id}/tasks"], method = [RequestMethod.GET])
    fun getTaskTypeTasks(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val taskType = taskTypeService.findById(id)
        return if (!taskType.isPresent) {
            ResponseEntity("task type not found", HttpStatus.NOT_FOUND)
        } else {
            ResponseEntity(taskType.get().tasks, HttpStatus.OK)
        }
    }

}