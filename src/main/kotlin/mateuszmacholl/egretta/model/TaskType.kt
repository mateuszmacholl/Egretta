package mateuszmacholl.egretta.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
data class TaskType(
        @Id @GeneratedValue
        val id: Int?,
        val name: String?,
        @OneToMany(mappedBy = "type", targetEntity = Task::class, cascade = [CascadeType.ALL])
        @JsonIgnore
        val tasks: List<Task> = mutableListOf()
)
