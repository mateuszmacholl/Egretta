package mateuszmacholl.egretta.model

import mateuszmacholl.egretta.utils.TaskState
import org.springframework.format.annotation.DateTimeFormat
import java.util.*
import javax.persistence.*

@Entity
data class Task(
        @Id @GeneratedValue
        val id: Int? = null,
        var name: String?,
        var content: String?,
        @ManyToOne(fetch = FetchType.EAGER)
        var author: User?,
        @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
        var subject: Subject? = null,
        @DateTimeFormat(pattern = "yyyy/mm/dd")
        @Temporal(TemporalType.DATE)
        var date: Date? = null,
        @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
        @Enumerated(EnumType.STRING)
        var type: TaskType? = null,
        @Enumerated(EnumType.STRING)
        var state: TaskState = TaskState.UNDONE
)