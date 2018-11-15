package mateuszmacholl.egretta.controller

import mateuszmacholl.egretta.converter.ConverterContext
import mateuszmacholl.egretta.converter.SubjectConverter
import mateuszmacholl.egretta.dto.CreateSubjectDto
import mateuszmacholl.egretta.model.specification.SubjectSpec
import mateuszmacholl.egretta.service.SubjectService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@Validated
@RequestMapping(value = ["/subjects"])
class SubjectController {
    @Autowired
    lateinit var subjectService: SubjectService
    @Autowired
    lateinit var converterContext: ConverterContext

    @RequestMapping(value = [""], method = [RequestMethod.GET])
    fun getAll(subjectSpec: SubjectSpec, pageable: Pageable): ResponseEntity<*> {
        val subjects = subjectService.findAll(subjectSpec, pageable)
        return ResponseEntity(subjects, HttpStatus.OK)
    }

    @RequestMapping(value = ["/{id}"], method = [RequestMethod.GET])
    fun getById(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val subject = subjectService.findById(id)
        return if (!subject.isPresent) {
            ResponseEntity("subject not found", HttpStatus.NOT_FOUND)
        } else {
            ResponseEntity(subject, HttpStatus.OK)
        }
    }

    @RequestMapping(value = [""], method = [RequestMethod.POST])
    fun add(@RequestBody @Validated createSubjectDto: CreateSubjectDto): ResponseEntity<*> {
        val subject = converterContext.get(SubjectConverter::class.java).convert(createSubjectDto)
        subjectService.add(subject)
        return ResponseEntity<Any>(HttpStatus.CREATED)
    }

    @RequestMapping(value = ["/{id}"], method = [RequestMethod.DELETE])
    fun delete(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val subject = subjectService.findById(id)
        return if (!subject.isPresent) {
            ResponseEntity("subject not found", HttpStatus.NOT_FOUND)
        } else {
            subjectService.delete(subject.get())
            return ResponseEntity<Any>(HttpStatus.NO_CONTENT )
        }
    }

    @RequestMapping(value = ["/{id}/tasks"], method = [RequestMethod.GET])
    fun getSubjectTasks(@PathVariable(value = "id") id: Int): ResponseEntity<*> {
        val subject = subjectService.findById(id)
        return if (!subject.isPresent) {
            ResponseEntity("subject not found", HttpStatus.NOT_FOUND)
        } else {
            ResponseEntity(subject.get().tasks, HttpStatus.OK)
        }
    }

}