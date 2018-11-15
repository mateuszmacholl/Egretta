package mateuszmacholl.egretta.controller

import mateuszmacholl.egretta.converter.ConverterContext
import mateuszmacholl.egretta.converter.TaskConverter
import mateuszmacholl.egretta.dto.CreateTaskDto
import mateuszmacholl.egretta.dto.UpdateStateTaskDto
import mateuszmacholl.egretta.model.specification.TaskSpec
import mateuszmacholl.egretta.service.TaskService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@Validated
@RequestMapping(value = ["/tasks"])
class TaskController {
    @Autowired
    lateinit var taskService: TaskService
    @Autowired
    lateinit var converterContext: ConverterContext

    @RequestMapping(value = [""], method = [RequestMethod.GET])
    fun getAll(taskSpec: TaskSpec,  pageable: Pageable): ResponseEntity<*> {
        val tasks = taskService.findAll(taskSpec, pageable)
        return ResponseEntity(tasks, HttpStatus.OK)
    }

    @RequestMapping(value = ["/{id}"], method = [RequestMethod.GET])
    fun getById(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val task = taskService.findById(id)
        return if (!task.isPresent) {
            ResponseEntity("task not found", HttpStatus.NOT_FOUND)
        } else {
            ResponseEntity(task, HttpStatus.OK)
        }
    }

    @RequestMapping(value = [""], method = [RequestMethod.POST])
    fun add(@RequestBody @Validated createTaskDto: CreateTaskDto): ResponseEntity<*> {
        val task = converterContext.get(TaskConverter::class.java).convert(createTaskDto)
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

    @RequestMapping(value = ["/{id}/state"], method = [RequestMethod.PUT])
    fun updateState(@PathVariable(value = "id") id: Int,
                    @RequestBody @Validated updateStateTaskDto: UpdateStateTaskDto): ResponseEntity<*> {
        val task = taskService.findById(id)
        return if (!task.isPresent) {
            ResponseEntity("task not found", HttpStatus.NOT_FOUND)
        } else {
            task.get().state = updateStateTaskDto.state
            taskService.add(task.get())
            return ResponseEntity<Any>(HttpStatus.NO_CONTENT)
        }
    }

}