package workshopkt.start

import io.ktor.application.*
import io.ktor.features.ContentNegotiation
import io.ktor.jackson.jackson
import io.ktor.routing.*

fun main(args: Array<String>): Unit = io.ktor.server.jetty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.server(testing: Boolean = false) {

    install(ContentNegotiation) {
        jackson()
    }
    install(Routing) {
        weather()
    }
}

