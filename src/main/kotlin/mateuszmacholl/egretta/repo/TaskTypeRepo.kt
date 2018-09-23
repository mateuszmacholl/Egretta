package mateuszmacholl.egretta.repo

import mateuszmacholl.egretta.model.TaskType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface TaskTypeRepo : JpaRepository<TaskType, Int>, JpaSpecificationExecutor<TaskType> {
    fun findByName(@Param("name") name: String): TaskType?
}