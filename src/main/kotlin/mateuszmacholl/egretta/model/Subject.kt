package mateuszmacholl.egretta.model

import com.fasterxml.jackson.annotation.JsonIgnore
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