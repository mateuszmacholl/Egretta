package mateuszmacholl.egretta.repo

import mateuszmacholl.egretta.model.Subject
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface SubjectRepo : PagingAndSortingRepository<Subject, Int>, JpaSpecificationExecutor<Subject> {
    fun findByName(@Param("name") name: String): Subject?
}