package mateuszmacholl.egretta.model.specification

import mateuszmacholl.egretta.model.Task
import net.kaczmarzyk.spring.data.jpa.domain.Equal
import net.kaczmarzyk.spring.data.jpa.domain.Like
import net.kaczmarzyk.spring.data.jpa.web.annotation.And
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec
import org.springframework.data.jpa.domain.Specification


@And(
        Spec(path = "name", spec = Like::class),
        Spec(path = "content", spec = Like::class),
        Spec(path = "state", spec = Equal::class),
        Spec(path = "date", spec = Equal::class)
)
interface TaskSpec : Specification<Task>