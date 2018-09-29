package mateuszmacholl.egretta.model.specification

import mateuszmacholl.egretta.model.Task
import net.kaczmarzyk.spring.data.jpa.domain.Equal
import net.kaczmarzyk.spring.data.jpa.domain.GreaterThan
import net.kaczmarzyk.spring.data.jpa.domain.Like
import net.kaczmarzyk.spring.data.jpa.web.annotation.And
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec
import org.springframework.data.jpa.domain.Specification

@And(
        Spec(path = "name", spec = Like::class),
        Spec(path = "content", spec = Like::class),
        Spec(path = "state", spec = Equal::class),
        Spec(path = "date", spec = Equal::class),
        Spec(path="date", params= ["dateAfter"], spec= GreaterThan::class),
        Spec(path = "subject.name", params = ["subject"], spec = Like::class),
        Spec(path = "type.name", params = ["type"], spec = Like::class),
        Spec(path = "author.username", params = ["author"], spec = Equal::class)
)
interface TaskSpec : Specification<Task>