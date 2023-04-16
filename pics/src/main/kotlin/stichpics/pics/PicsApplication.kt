package stichpics.pics

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PicsApplication

fun main(args: Array<String>) {
	runApplication<PicsApplication>(*args)
}
