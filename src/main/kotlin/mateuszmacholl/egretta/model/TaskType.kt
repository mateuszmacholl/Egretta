package mateuszmacholl.egretta.model

import com.fasterxml.jackson.annotation.JsonIgnore
import mateuszmacholl.egretta.model.task.Task
import javax.persistence.*

@Entity
data class TaskType(
        val name: String?,
        @OneToMany(mappedBy = "type", targetEntity = Task::class, cascade = [CascadeType.ALL])
        @JsonIgnore
        val tasks: List<Task> = mutableListOf()
){
        @Id @GeneratedValue
        var id: Int? = null
}
