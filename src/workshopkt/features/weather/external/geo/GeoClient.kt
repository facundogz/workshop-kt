package workshopkt.features.weather.external.geo

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import okhttp3.Response
import workshopkt.features.weather.external.geo.GeoSpot.GeoLocation
import workshopkt.features.weather.external.metilo.MetiloResponse
import java.lang.RuntimeException
import java.util.concurrent.TimeUnit
import kotlin.random.Random
import com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES as UnknownProps
import okhttp3.OkHttpClient.Builder as Client
import okhttp3.Request.Builder as NewRequest

/**
 * This file contains a lot of Kotlin features
 * - Type aliases
 * - Compile-time constants
 * - Properties and lazy loading properties
 * - String interpolation
 * - Scope functions
 * - Elvis operator
 * - Safe navigation
 * - Throw expressions
 */
object GeoClient {

    private const val url = "http://<SPOTS>"
    private const val usr = "facu"

    private val uow get() = "workshop-kt-$usr-${Random.nextInt(1000,9999)}"
    private const val xclient = "workshop-kt"

    private val mapper by lazy {
        jacksonObjectMapper()
            .apply {
                propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
                disable(UnknownProps)
                registerModule(JavaTimeModule())
            }
    }


    fun getCitySpot(oid: Int): GeoLocation {
        return NewRequest()
            .addHeader("X-Client", xclient)
            .addHeader("X-UOW", uow)
            .url("$url/CIT_$oid")
            .build()
            .let {
                Client().callTimeout(30,TimeUnit.SECONDS).build()
                    .newCall(it).execute()
                    .takeIf(Response::isSuccessful)?.body?.string() ?: throw RuntimeException("Cannot get Spot")
            }
            .let {
                mapper.readValue<GeoSpot>(it).location
            }
    }
}
