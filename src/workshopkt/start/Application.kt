package workshopkt.start

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.routing.*
import io.ktor.auth.*
import io.ktor.client.*
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.jackson.jackson
import org.litote.kmongo.include
import workshopkt.features.weather.weatherRouting

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
        jackson {
            setDefaultPropertyInclusion(JsonInclude.Include.NON_EMPTY)
            registerModule(JavaTimeModule())
        }
    }

    install(Routing) {
        RoutingModule(this@server)
        weatherRouting()
    }
}

