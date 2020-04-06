package workshopkt.features.suggestions.model

/**
 * A prototype contains a lot of information, we are interested only in a few fields:
 *
 * id: <string>
 * destination: {
 *    id: <string>      -> our GID
 *    name: <string>    -> name of the city
 * }
 *
 */
data class Prototype(
    val id: String,
    val destination: PrototypeCityHolder
) {
    data class PrototypeCityHolder(
        val id: String,
        val name: String
    )
}