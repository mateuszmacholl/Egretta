package mateuszmacholl.egretta.service

import mateuszmacholl.egretta.model.Subject
import mateuszmacholl.egretta.model.specification.SubjectSpec
import mateuszmacholl.egretta.repo.SubjectRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class SubjectService {
    @Autowired
    lateinit var subjectRepo: SubjectRepo

    fun findById(id: Int): Optional<Subject> {
        return subjectRepo.findById(id)
    }

    fun findAll(subjectSpec: SubjectSpec, pageable: Pageable): MutableIterable<Subject> {
        return subjectRepo.findAll(subjectSpec, pageable)
    }

    fun add(subject: Subject) {
        subjectRepo.save(subject)
    }

    fun delete(subject: Subject) {
        subjectRepo.delete(subject)
    }

    fun findByName(name: String): Subject?{
        return subjectRepo.findByName(name)
    }
}