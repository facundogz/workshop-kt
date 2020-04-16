package workshopkt.start

import io.ktor.application.*
import io.ktor.response.respond
import io.ktor.routing.*
import io.ktor.util.toMap
import workshopkt.features.calculator.CalculatorService
import workshopkt.features.lorem.LoremService
import workshopkt.features.suggestions.SuggestionsService


object RoutingModule: ((Application) -> Routing) {
    override fun invoke(module: Application) = module
        .routing {

            get("lorem/{quantity?}") {
                call.respond(mapOf("data" to LoremService.generate(call.parameters["quantity"].orEmpty().toIntOrNull())))
            }

            get("suggestions/{hint?}") {
                call.parameters["hint"]
                    .orEmpty()
                    .run (SuggestionsService)
                    .let { call.respond(it) }            }

            get("healthCheck") {
                call.respond(mapOf("status" to "OK"))
            }

            get("calculator/{operand1}/{op}/{operand2}") {
                call.parameters.toMap()
                    .mapValues { it.value.first() }
                    .run  {
                        CalculatorService.operate(
                            getOrDefault("operand1", "0").toInt(),
                            getOrDefault("operand2", "0").toInt(),
                            getOrDefault("op", "+")
                        )
                    }.let {
                        call.respond(mapOf("result" to it))
                    }
            }

            get("calculator/{...}") {
                call.respond("""Usage: calculator/{operand1}/{op}/{operand2}
                            |Example: calculator/4/*/2
                            |Available operations: +,-,*,div,mod"""
                    .trimMargin())
            }
    }
}

