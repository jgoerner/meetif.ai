/*
 * Meetif.ai - Meetup Knowledge Graph
 *
 * TODO link member and events
 *
 */
package meetifai

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class MeetifaiApplication

fun main(args: Array<String>) {
    runApplication<MeetifaiApplication>(*args)
}