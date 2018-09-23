package mateuszmacholl.egretta.service

import mateuszmacholl.egretta.model.Task
import mateuszmacholl.egretta.model.specification.TaskSpec
import mateuszmacholl.egretta.repo.TaskRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*



@Service
class TaskService {
    @Autowired
    lateinit var taskRepo: TaskRepo

    fun findById(id: Int): Optional<Task> {
        return taskRepo.findById(id)
    }

    fun findAll(taskSpec: TaskSpec, pageable: Pageable): MutableIterable<Task> {
        return taskRepo.findAll(taskSpec, pageable)
    }

    fun add(task: Task) {
        taskRepo.save(task)
    }

    fun delete(task: Task) {
        taskRepo.delete(task)
    }
}