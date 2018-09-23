package mateuszmacholl.egretta.model

import com.fasterxml.jackson.annotation.JsonIgnore
import mateuszmacholl.egretta.utils.TaskState
import org.springframework.format.annotation.DateTimeFormat
import java.util.*
import javax.persistence.*

@Entity
data class Task(
        @Id @GeneratedValue
        val id: Int? = null,
        val name: String?,
        val content: String?,
        @ManyToOne(fetch = FetchType.EAGER)
        @JsonIgnore
        val author: User?,
        @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
        @JsonIgnore
        val subject: Subject? = null,
        @DateTimeFormat(pattern = "yyyy/mm/dd")
        @Temporal(TemporalType.DATE)
        val date: Date? = null,
        @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
        @JsonIgnore
        val type: TaskType? = null,
        @Enumerated(EnumType.STRING)
        val state: TaskState = TaskState.UNDONE
)