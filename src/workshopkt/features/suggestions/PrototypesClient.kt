package workshopkt.features.suggestions

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import okhttp3.Response
import workshopkt.features.suggestions.model.PrototypesResponse
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
object PrototypesClient {

    private const val url = "http://<COMPLETAR>"
    private const val usr = "facu"

    private val uow get() = "workshop-kt-$usr-${Random.nextInt(1000,9999)}"
    private const val client = "workshop-kt"

    private val mapper by lazy { jacksonObjectMapper().configure(UnknownProps, false) }

    val prototypeList by lazy {
        NewRequest()
            .addHeader("X-Client", client)
            .addHeader("X-UOW", uow)
            .url("$url/prototypes?include=all&limit=4000")
            .build()
            .let {
                Client().callTimeout(30,TimeUnit.SECONDS).build()
                    .newCall(it).execute()
                    .takeIf(Response::isSuccessful)?.body?.string() ?: throw RuntimeException("Cannot read Prototype list")
            }
            .let {
                mapper.readValue<PrototypesResponse>(it).data
            }
    }
}