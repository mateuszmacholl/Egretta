package mateuszmacholl.egretta.converter

import mateuszmacholl.egretta.dto.CreateSubjectDto
import mateuszmacholl.egretta.model.Subject
import org.springframework.stereotype.Service

@Service
class SubjectConverter {
    fun toEntity(createSubjectDto: CreateSubjectDto): Subject {
        return Subject(
                null,
                createSubjectDto.name
        )
    }

    fun toEntity(createSubjectDtos: List<CreateSubjectDto>): List<Subject> {
        return createSubjectDtos.map { s -> toEntity(s) }.toList()
    }
}