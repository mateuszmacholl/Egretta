package mateuszmacholl.egretta.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import mateuszmacholl.egretta.model.task.Task
import org.springframework.format.annotation.DateTimeFormat
import java.util.*
import javax.persistence.*

@Entity
data class User(
    var username: String? = null,

    var email: String? = null,

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    var password: String? = null,

    @DateTimeFormat
    var creationDate: Calendar? = null,

    var enabled: Boolean? = null,

    @ElementCollection(fetch = FetchType.EAGER)
    var roles: MutableList<String> = mutableListOf(),

    @JsonIgnore
    @OneToMany(mappedBy = "author", targetEntity = Task::class,  cascade = [CascadeType.ALL])
    var tasks: MutableList<Task> = mutableListOf()
){
    @Id
    @GeneratedValue
    var id: Int? = null

    init {
        roles.add("user")
        enabled = true
        creationDate = Calendar.getInstance()
    }
}