package mateuszmacholl.egretta.service

import mateuszmacholl.egretta.model.TaskType
import mateuszmacholl.egretta.model.specification.TaskTypeSpec
import mateuszmacholl.egretta.repo.TaskTypeRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class TaskTypeService {
    @Autowired
    lateinit var taskTypeRepo: TaskTypeRepo

    fun findById(id: Int): Optional<TaskType> {
        return taskTypeRepo.findById(id)
    }

    fun findAll(taskTypeSpec: TaskTypeSpec, pageable: Pageable): MutableIterable<TaskType> {
        return taskTypeRepo.findAll(taskTypeSpec, pageable)
    }

    fun add(taskType: TaskType) {
        taskTypeRepo.save(taskType)
    }

    fun delete(taskType: TaskType) {
        taskTypeRepo.delete(taskType)
    }

    fun findByName(name: String): TaskType?{
        return taskTypeRepo.findByName(name)
    }
}