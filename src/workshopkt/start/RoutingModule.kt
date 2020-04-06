package workshopkt.start

import io.ktor.application.*
import io.ktor.response.respond
import io.ktor.routing.*
import io.ktor.util.toMap
import workshopkt.features.calculator.CalculatorService


object RoutingModule: ((Application) -> Routing) {
    override fun invoke(module: Application) = module
        .routing {

            get("lorem/{quantity?}") {
                // You can access path parameters using indexing:
                // call.parameter["someParam"] -> careful! could be null
            }

            get("suggestions/{hint?}") {
                // Your code
            }

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

