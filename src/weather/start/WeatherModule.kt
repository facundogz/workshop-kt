package weather.start

import io.ktor.application.*
import io.ktor.http.toHttpDateString
import io.ktor.response.respond
import io.ktor.routing.*
import io.ktor.util.getOrFail
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt
import kotlin.random.Random

private val forecast = listOf("CLOUDY", "CLEAR", "RAIN", "ELECTRIC_STORM", "TORNADO", "CATACLYSM")
private val validate : (Pair<Double,Double>) -> Boolean = { (lat, lon) -> lat in (-90.0..90.0) && lon in (-180.0..180.0) }
private val generate : (Random) -> Map<String, Any> = {
    (it.nextInt(-10, 43)).let { min ->
        mapOf(
            "forecast" to forecast.random(it),
            "min" to min,
            "max" to min + it.nextInt(2, 7)
        )
    }
}

fun Route.weather() {
    get("weather/{geolocation}") {
        call.parameters.getOrFail("geolocation").run {
            split(",")
        }.takeIf { it.size == 2 }
                ?.run { first().toDouble() to last().toDouble() }
                ?.takeIf(validate)
                ?.let { (lat, lon) -> Random((lat * 1000 + lon * 1000 + LocalDate.now().dayOfYear).roundToInt()) }
                ?.run(generate)
                ?.plus("date" to LocalDate.now().format(DateTimeFormatter.ISO_DATE))
                .let {
                    call.respond(it ?: emptyMap<String, Any>())
                }

    }

    get("weather/{geolocation}/{date}") {
        call.parameters.getOrFail("geolocation").run {
                split(",")
            }.takeIf { it.size == 2 }
                ?.run { first().toDouble() to last().toDouble() }
                ?.takeIf(validate)
                ?.takeIf {
                    LocalDate.parse(call.parameters.getOrFail("date")).run {
                        isAfter(LocalDate.now()) && isBefore(LocalDate.now().plusMonths(2))
                    }
                }
                ?.let { (lat, lon) -> Random((lat * 1000 + lon * 1000 + LocalDate.parse(call.parameters.getOrFail("date")).dayOfYear).roundToInt()) }
                ?.run(generate)
                ?.plus("date" to call.parameters.getOrFail("date"))
                .let {
                    call.respond(it ?: emptyMap<String, Any>())
            }
    }
}

