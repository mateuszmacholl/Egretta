package mateuszmacholl.egretta.model

import com.fasterxml.jackson.annotation.JsonIgnore
import mateuszmacholl.egretta.model.task.Task
import javax.persistence.*

@Entity
class Subject(
        @Id @GeneratedValue
        val id: Int?,
        val name: String?,
        @OneToMany(mappedBy = "subject", targetEntity = Task::class,  cascade = [CascadeType.ALL])
        @JsonIgnore
        val tasks: List<Task> = mutableListOf()
)