package mateuszmacholl.egretta.converter

import mateuszmacholl.egretta.dto.CreateSubjectDto
import mateuszmacholl.egretta.model.Subject
import org.springframework.stereotype.Service

@Service
class SubjectConverter: Converter<CreateSubjectDto, Subject> {
    override fun convert(from: CreateSubjectDto): Subject {
        return Subject(
                from.name
        )
    }

    override fun convert(from: List<CreateSubjectDto>): List<Subject> {
        return from.map { s -> convert(s) }.toList()
    }
}