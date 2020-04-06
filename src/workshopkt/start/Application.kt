package workshopkt.start

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.routing.*
import io.ktor.auth.*
import io.ktor.client.*
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.jackson.jackson

fun main(args: Array<String>): Unit = io.ktor.server.jetty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.server(testing: Boolean = false) {
    install(Compression) {
        gzip {
            priority = 1.0
        }
        deflate {
            priority = 10.0
            minimumSize(1024)
        }
    }

    install(Authentication)

    install(ContentNegotiation) {
        jackson {}
    }

    val client = HttpClient(OkHttp)

    install(Routing) {
        RoutingModule(this@server)
    }
}

