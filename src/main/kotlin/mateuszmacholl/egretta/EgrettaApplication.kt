package mateuszmacholl.egretta

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableAsync
@ComponentScan("mateuszmacholl.egretta")
class EgrettaApplication

fun main(args: Array<String>) {
    runApplication<EgrettaApplication>(*args)
}
