package mateuszmacholl.egretta.repo

import mateuszmacholl.egretta.model.task.Task
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface TaskRepo : PagingAndSortingRepository<Task, Int>, JpaSpecificationExecutor<Task> {
}